package emu.jmusic;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import emu.main.EMusic;
import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.NoteContainer;
import emu.music.Composition;
import emu.music.mediator.ICompositionPlayer;
import emu.music.mediator.ICompositionProperties;
import emu.music.properties.NoteDimension;
import emu.music.properties.TimeSignature;
import emu.util.EMath;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.Write;

public class JMusicPlayer implements ICompositionPlayer {
    public JMusicPlayer() {
        // to prevent delay later
        Play.midi(new Score(), false, false, 1, 0);
    }

    @Override
    public void playChord(Chord chord, ICompositionProperties compositionProperties) {
        try {
            Play.midi(convertChord(chord, compositionProperties), false, false, 1, 0);
        } catch (Exception e) {
        }
    }

    @Override
    public void playChords(List<Entry<Duration, Chord>> chords, ICompositionProperties compositionProperties) {
        Play.midi(convertChords(chords, compositionProperties, true), false, false, 1, 0);
    }

    public static Composition compositionFromFile(File file, ICompositionProperties compositionProperties) {
        Score score = new Score();
        Read.midi(score, file.getPath());
        return convertScore(score, compositionProperties);
    }

    public static void compositionToFile(File file, Composition composition, ICompositionProperties compositionProperties) {
        Write.midi(convertChords(composition.chordList(), compositionProperties, false), file.getPath());

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

    public static Score convertChord(Chord chord, ICompositionProperties compositionProperties) {
        Duration meter = compositionProperties.getTimeSignature().getMeter();
        double tempo = compositionProperties.getTempo();
        Score score = createScore(meter, tempo);
        NoteContainer[] notes = chord.getNotes();
        for (int i = 0; i < notes.length; i++) {
            int instrument = compositionProperties.getInstruments()[i];
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

    public static Score convertChords(List<Entry<Duration, Chord>> chords, ICompositionProperties compositionProperties, boolean offset) {
        int[] instruments = compositionProperties.getInstruments();
        Duration meter = compositionProperties.getTimeSignature().getMeter();
        double tempo = compositionProperties.getTempo();
        Score score = createScore(meter, tempo);
        Duration startOffset = offset ? compositionProperties.getPlayerStart() : Duration.ZERO;
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

    public static Composition convertScore(Score score, ICompositionProperties compositionProperties) {
        if (score.getPartArray().length > EMusic.NUMBER_OF_VOICES) {
            throw new IllegalStateException("Score has " + score.getPartArray().length + " parts. Maximum is " + EMusic.NUMBER_OF_VOICES);
        }

        setProperties(score, compositionProperties);

        Composition composition = new Composition();
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
                    composition.addNote(EMath.guessDuration(phrase.getNoteStartTime(i) / 4), note, voice);
                }
            }
            instruments[voice++] = part.getInstrument();
        }
        compositionProperties.setInstruments(instruments);
        return composition;
    }

    private static void setProperties(Score score, ICompositionProperties compositionProperties) {
        TimeSignature oldTimeSignature = compositionProperties.getTimeSignature();
        TimeSignature newTimeSignature = new TimeSignature(score.getNumerator(), score.getDenominator());
        newTimeSignature.setNoteDuration(oldTimeSignature.getNoteDuration());
        newTimeSignature.setDivision(oldTimeSignature.getDivision());
        compositionProperties.setTimeSignature(newTimeSignature);
        compositionProperties.setTempo(EMath.round(score.getTempo()));
    }
}
