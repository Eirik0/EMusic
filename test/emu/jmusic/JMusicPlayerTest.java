package emu.jmusic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.Composition;
import emu.music.TestMusicCreator;
import emu.music.mediator.TestCompositionProperties;
import emu.music.properties.TimeSignature;
import jm.constants.Durations;
import jm.constants.Pitches;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class JMusicPlayerTest {
    @Test
    public void testConvertPitch() {
        assertEquals(Pitches.C0, JMusicPlayer.convertPitch(Note.C0));
        assertEquals(Pitches.C4, JMusicPlayer.convertPitch(Note.C4));
        assertEquals(Pitches.C9, JMusicPlayer.convertPitch(Note.C9));
    }

    @Test
    public void testConvertKey() {
        assertEquals(Note.C0, JMusicPlayer.convertKey(Pitches.C0));
        assertEquals(Note.C4, JMusicPlayer.convertKey(Pitches.C4));
        assertEquals(Note.C9, JMusicPlayer.convertKey(Pitches.C9));
    }

    @Test
    public void testConvertDuration() {
        assertEquals(Durations.QUARTER_NOTE, JMusicPlayer.convertDuration(new Duration(1, 4)), 0.000001);
        assertEquals(Durations.SIXTEENTH_NOTE, JMusicPlayer.convertDuration(new Duration(1, 16)), 0.000001);
    }

    @Test
    public void testConvertNote() {
        Note note = TestMusicCreator.halfNote(Note.C4);
        jm.music.data.Note convertNote = JMusicPlayer.convertNote(note);
        assertEquals(Pitches.C4, convertNote.getPitch());
        assertEquals(Durations.HALF_NOTE, convertNote.getRhythmValue(), 0.000001);
    }

    @Test
    public void testCreateScore() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.setTempo(123);
        testProperties.getTimeSignature().setMeter(2, 6);
        Score score = JMusicPlayer.createScore(testProperties.getTimeSignature().getMeter(), testProperties.getTempo());
        assertEquals(123, score.getTempo(), 0.000001);
        assertEquals(2, score.getNumerator());
        assertEquals(6, score.getDenominator());
    }

    @Test
    public void testConvertChord() {
        Chord chord = TestMusicCreator.newChord();
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getInstruments()[0] = 5;
        Score score = JMusicPlayer.convertChord(chord, testProperties);
        assertEquals(1, score.getPartArray().length);
        Part part = score.getPart(0);
        assertEquals(1, part.getPhraseArray().length);
        assertEquals(5, part.getInstrument());
        Phrase phrase = part.getPhrase(0);
        assertEquals(1, phrase.getNoteArray().length);
        jm.music.data.Note note = phrase.getNote(0);
        assertEquals(JMusicPlayer.convertPitch(chord.getSingleNote(0).key), note.getPitch());
        assertEquals(JMusicPlayer.convertDuration(chord.getSingleNote(0).duration), note.getRhythmValue(), 0.000001);
    }

    @Test
    public void testConvertComposition() {
        Composition composition = TestMusicCreator.newWholeNoteScale(0);
        composition.addNote(Duration.WHOLE_BEAT, new Note(Note.C4, Duration.WHOLE_BEAT), 3);
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getInstruments()[0] = 5;
        testProperties.getInstruments()[3] = 3;

        Score score = JMusicPlayer.convertChords(composition.chordList(), testProperties, false);
        assertEquals(4, score.getPartArray().length);

        Part part1 = score.getPart(0);
        Part part2 = score.getPart(1);
        Part part3 = score.getPart(2);
        Part part4 = score.getPart(3);

        assertEquals(8, part1.getPhraseArray().length);
        assertEquals(5, part1.getInstrument());
        assertEquals(0, part2.getPhraseArray().length);
        assertEquals(0, part2.getInstrument());
        assertEquals(0, part3.getPhraseArray().length);
        assertEquals(0, part3.getInstrument());
        assertEquals(5, part1.getInstrument());
        assertEquals(1, part4.getPhraseArray().length);
        assertEquals(3, part4.getInstrument());

        assertEquals(1, part1.getPhrase(0).getNoteArray().length);

        jm.music.data.Note note1 = part1.getPhrase(0).getNote(0);
        jm.music.data.Note note2 = part1.getPhrase(1).getNote(0);
        jm.music.data.Note note3 = part1.getPhrase(2).getNote(0);
        jm.music.data.Note note4 = part1.getPhrase(3).getNote(0);
        jm.music.data.Note note5 = part1.getPhrase(4).getNote(0);
        jm.music.data.Note note6 = part1.getPhrase(5).getNote(0);
        jm.music.data.Note note7 = part1.getPhrase(6).getNote(0);
        jm.music.data.Note note8 = part1.getPhrase(7).getNote(0);

        ArrayList<Entry<Duration, Chord>> chordList = composition.chordList();
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 0, 0)), note1.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 1, 0)), note2.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 2, 0)), note3.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 3, 0)), note4.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 4, 0)), note5.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 5, 0)), note6.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 6, 0)), note7.getPitch());
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 7, 0)), note8.getPitch());
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 0, 0)), note1.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 1, 0)), note2.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 2, 0)), note3.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 3, 0)), note4.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 4, 0)), note5.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 5, 0)), note6.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 6, 0)), note7.getRhythmValue(), 0.000001);
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 7, 0)), note8.getRhythmValue(), 0.000001);

        Phrase phrase2 = part4.getPhrase(0);
        jm.music.data.Note note9 = phrase2.getNote(0);
        assertEquals(JMusicPlayer.convertPitch(getKey(chordList, 1, 3)), note9.getPitch());
        assertEquals(JMusicPlayer.convertDuration(getDuration(chordList, 1, 3)), note9.getRhythmValue(), 0.000001);
    }

    private static int getKey(ArrayList<Entry<Duration, Chord>> chordList, int chord, int note) {
        return chordList.get(chord).getValue().getNotes()[note].getNote().key;
    }

    private static Duration getDuration(ArrayList<Entry<Duration, Chord>> chordList, int chord, int note) {
        return chordList.get(chord).getValue().getNotes()[note].getNote().duration;
    }

    @Test
    public void testConvertScore_UpdateProperties() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.setInstruments(new int[] { 19, 17, 13, 11, 7, 5, 3, 2 });
        testProperties.setTempo(23);
        TimeSignature timeSignature = new TimeSignature(31, 29);
        timeSignature.setNoteDuration(new Duration(41, 37));
        timeSignature.setDivision(new Duration(47, 43));
        testProperties.setTimeSignature(timeSignature);
        Score score = createTestScore();
        JMusicPlayer.convertScore(score, testProperties);
        assertEquals(5, testProperties.getTimeSignature().getMeter().beat);
        assertEquals(4, testProperties.getTimeSignature().getMeter().division);
        assertEquals(timeSignature.getNoteDuration(), testProperties.getTimeSignature().getNoteDuration());
        assertEquals(timeSignature.getDivision(), testProperties.getTimeSignature().getDivision());
        assertEquals(255, testProperties.getTempo());
        assertEquals(1, testProperties.getInstruments()[0]);
        assertEquals(2, testProperties.getInstruments()[1]);
        assertEquals(3, testProperties.getInstruments()[2]);
        assertEquals(4, testProperties.getInstruments()[3]);
        assertEquals(5, testProperties.getInstruments()[4]);
    }

    @Test
    public void testConvertScore() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        Score score = createTestScore();
        Composition composition = JMusicPlayer.convertScore(score, testProperties);

        ArrayList<Entry<Duration, Chord>> chordList = composition.chordList();

        assertEquals(Duration.ZERO, chordList.get(0).getKey());
        Chord chord1 = chordList.get(0).getValue();
        assertEquals(1, chord1.numVoices());
        assertEquals(new Note(Note.C9, new Duration(3, 4)), chord1.getSingleNote(0));

        assertEquals(new Duration(1, 4), chordList.get(1).getKey());
        Chord chord2 = chordList.get(1).getValue();
        assertEquals(1, chord2.numVoices());
        assertEquals(new Note(Note.E8, new Duration(2, 4)), chord2.getSingleNote(0));

        assertEquals(new Duration(2, 4), chordList.get(2).getKey());
        Chord chord3 = chordList.get(2).getValue();
        assertEquals(1, chord3.numVoices());
        assertEquals(new Note(Note.G8, new Duration(1, 4)), chord3.getSingleNote(0));

        assertEquals(new Duration(5, 4), chordList.get(3).getKey());
        Chord chord4 = chordList.get(3).getValue();
        assertEquals(2, chord4.numVoices());
        assertEquals(new Note(Note.A8, new Duration(1, 4)), chord4.getSingleNote(2));
        assertEquals(new Note(Note.F8, new Duration(1, 4)), chord4.getSingleNote(3));

        assertEquals(new Duration(6, 4), chordList.get(4).getKey());
        Chord chord5 = chordList.get(4).getValue();
        assertEquals(2, chord5.numVoices());
        assertEquals(new Note(Note.C9, new Duration(1, 4)), chord5.getSingleNote(1));
        assertEquals(new Note(Note.D8, new Duration(1, 4)), chord5.getSingleNote(4));

        assertEquals(new Duration(7, 4), chordList.get(5).getKey());
        Chord chord6 = chordList.get(5).getValue();
        assertEquals(2, chord6.numVoices());
        assertEquals(new Note(Note.A8, new Duration(1, 4)), chord6.getSingleNote(2));
        assertEquals(new Note(Note.F8, new Duration(1, 4)), chord6.getSingleNote(3));

    }

    private static Score createTestScore() {
        Score score = new Score();
        score.setNumerator(5);
        score.setDenominator(4);
        score.setTempo(255);
        score.add(createPart1());
        score.add(createPart2());
        score.add(createPart3());
        score.add(createPart4());
        score.add(createPart5());
        return score;
    }

    private static Part createPart1() {
        Part part = new Part(1);
        Phrase phrase1 = new Phrase();
        phrase1.addNote(JMusicPlayer.convertPitch(Note.C9), 3);
        part.addPhrase(phrase1);
        Phrase phrase2 = new Phrase(2.0);
        phrase2.addNote(JMusicPlayer.convertPitch(Note.G8), 1);
        part.addPhrase(phrase2);
        Phrase phrase3 = new Phrase(1.0);
        phrase3.addNote(JMusicPlayer.convertPitch(Note.E8), 2);
        part.addPhrase(phrase3);
        return part;
    }

    private static Part createPart2() {
        Part part = new Part(2);
        Phrase phrase = new Phrase(6.0);
        phrase.addNote(JMusicPlayer.convertPitch(Note.C9), 1);
        part.add(phrase);
        return part;
    }

    private static Part createPart3() {
        Part part = new Part(3);
        part.addNote(JMusicPlayer.convertNote(new Note(Note.A8, Duration.QUARTER_BEAT)), 5.0);
        part.addNote(JMusicPlayer.convertNote(new Note(Note.A8, Duration.QUARTER_BEAT)), 7.0);
        return part;
    }

    private static Part createPart4() {
        Part part = new Part(4);
        part.addNote(JMusicPlayer.convertNote(new Note(Note.F8, Duration.QUARTER_BEAT)), 5.0);
        part.addNote(JMusicPlayer.convertNote(new Note(Note.F8, Duration.QUARTER_BEAT)), 7.0);
        return part;
    }

    private static Part createPart5() {
        Part part = new Part(5);
        part.addNote(JMusicPlayer.convertNote(new Note(Note.D8, Duration.QUARTER_BEAT)), 6.0);
        return part;
    }
}
