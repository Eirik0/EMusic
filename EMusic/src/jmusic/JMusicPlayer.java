package jmusic;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.Write;
import main.EMusic;
import music.Chord;
import music.Duration;
import music.Note;
import music.NoteContainer;
import music.Song;
import music.mediator.ISongPlayer;
import music.mediator.ISongProperties;
import music.properties.NoteDimension;
import music.properties.TimeSignature;
import util.EMath;

public class JMusicPlayer implements ISongPlayer {
	public JMusicPlayer() {
		// to prevent delay later
		Play.midi(new Score(), false, false, 1, 0);
	}

	@Override
	public void playChord(Chord chord, ISongProperties songProperties) {
		try {
			Play.midi(convertChord(chord, songProperties), false, false, 1, 0);
		} catch (Exception e) {
		}
	}

	@Override
	public void playChords(List<Entry<Duration, Chord>> chords, ISongProperties songProperties) {
		Play.midi(convertChords(chords, songProperties, true), false, false, 1, 0);
	}

	public static Song songFromFile(File file, ISongProperties songProperties) {
		Score score = new Score();
		Read.midi(score, file.getPath());
		return convertScore(score, songProperties);
	}

	public static void songToFile(File file, Song song, ISongProperties songProperties) {
		Write.midi(convertChords(song.chordList(), songProperties, false), file.getPath());

	}

	private static Phrase createPhrase(Note note, double startTime) {
		return new Phrase(convertNote(note), startTime);
	}

	private static Part createPart(int instrument) {
		return new Part(instrument);
	}

	public static Score createScore(Duration meter, double tempo) {
		Score score = new Score();
		score.setTempo(tempo);
		score.setNumerator((int) meter.beat);
		score.setDenominator((int) meter.division);
		return score;
	}

	// EMusic -> jMusic
	public static int convertPitch(int key) {
		return NoteDimension.TOTAL_NOTES + jm.constants.Pitches.C0 - key - 1;
	}

	public static double convertDuration(Duration duration) {
		return 4 * duration.doubleValue();
	}

	public static jm.music.data.Note convertNote(Note note) {
		return new jm.music.data.Note(convertPitch(note.key), convertDuration(note.duration));
	}

	public static Score convertChord(Chord chord, ISongProperties songProperties) {
		Duration meter = songProperties.getTimeSignature().getMeter();
		double tempo = songProperties.getTempo();
		Score score = createScore(meter, tempo);
		NoteContainer[] notes = chord.getNotes();
		for (int i = 0; i < notes.length; i++) {
			int instrument = songProperties.getInstruments()[i];
			if (notes[i] != null) {
				Note note = notes[i].getNote();
				Part part = createPart(instrument);
				part.add(createPhrase(note, 0));
				for (Note additionalNote : notes[i].getAdditionalNotes()) {
					part.addPhrase(createPhrase(additionalNote, 0));
				}
				score.add(part);
			}
		}
		return score;
	}

	public static Score convertChords(List<Entry<Duration, Chord>> chords, ISongProperties songProperties, boolean offset) {
		int[] instruments = songProperties.getInstruments();
		Duration meter = songProperties.getTimeSignature().getMeter();
		double tempo = songProperties.getTempo();
		Score score = createScore(meter, tempo);
		Duration startOffset = offset ? songProperties.getPlayerStart() : Duration.ZERO;
		for (Entry<Duration, Chord> durationChord : chords) {
			Duration noteStart = durationChord.getKey().subtract(startOffset);
			NoteContainer[] notes = durationChord.getValue().getNotes();
			for (int i = 0; i < notes.length; i++) {
				if (notes[i] != null) {
					if (score.getSize() <= i) {
						int inst = score.getSize();
						while (score.getSize() <= i) {
							Part part = createPart(instruments[inst++]);
							score.add(part);
						}
					}
					Part part = score.getPart(i);
					part.addPhrase(createPhrase(notes[i].getNote(), convertDuration(noteStart)));
					for (Note additionalNote : notes[i].getAdditionalNotes()) {
						part.addPhrase(createPhrase(additionalNote, convertDuration(noteStart)));
					}
				}
			}
		}
		return score;
	}

	// jMusic -> EMusic
	public static int convertKey(int pitch) {// same as convertPitch
		return NoteDimension.TOTAL_NOTES + jm.constants.Pitches.C0 - pitch - 1;
	}

	public static Song convertScore(Score score, ISongProperties songProperties) {
		if (score.getPartArray().length > EMusic.NUMBER_OF_VOICES) {
			throw new IllegalStateException("Score has " + score.getPartArray().length + " parts. Maximum is " + EMusic.NUMBER_OF_VOICES);
		}

		setProperties(score, songProperties);

		Song song = new Song();
		int[] instruments = new int[EMusic.NUMBER_OF_VOICES];

		int voice = 0;
		for (Part part : score.getPartArray()) {
			for (Phrase phrase : part.getPhraseArray()) {
				jm.music.data.Note[] noteArray = phrase.getNoteArray();
				for (int i = 0; i < noteArray.length; i++) {
					jm.music.data.Note jMNote = noteArray[i];
					if (jMNote.getPitch() == jm.music.data.Note.REST) {
						continue;
					}
					Note note = new Note(convertKey(jMNote.getPitch()), EMath.guessDuration(jMNote.getRhythmValue() / 4));
					song.addNote(EMath.guessDuration(phrase.getNoteStartTime(i) / 4), note, voice);
				}
			}
			instruments[voice++] = part.getInstrument();
		}
		songProperties.setInstruments(instruments);
		return song;
	}

	private static void setProperties(Score score, ISongProperties songProperties) {
		TimeSignature oldTimeSignature = songProperties.getTimeSignature();
		TimeSignature newTimeSignature = new TimeSignature(score.getNumerator(), score.getDenominator());
		newTimeSignature.setNoteDuration(oldTimeSignature.getNoteDuration());
		newTimeSignature.setDivision(oldTimeSignature.getDivision());
		songProperties.setTimeSignature(newTimeSignature);
		songProperties.setTempo(EMath.round(score.getTempo()));
	}
}
