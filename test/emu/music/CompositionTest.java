package emu.music;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CompositionTest {
    @Test
    public void testAdd_OneNote_OneChord() {
        Composition composition = new Composition();
        composition.addNote(Duration.ZERO, TestMusicCreator.QUARTER_NOTE, 0);
        List<Map.Entry<Duration, Chord>> chords = composition.chordList();
        assertEquals(1, chords.size());
        assertEquals(Duration.ZERO, chords.get(0).getKey());
        assertEquals(TestMusicCreator.QUARTER_NOTE, chords.get(0).getValue().getSingleNote(0));
    }

    @Test
    public void testAdd_TwoNotes_OneChord() {
        Composition composition = new Composition();
        Note note1 = TestMusicCreator.quarterNote(1);
        Note note2 = TestMusicCreator.quarterNote(2);
        composition.addNote(Duration.ZERO, note1, 0);
        composition.addNote(Duration.ZERO, note2, 1);
        List<Map.Entry<Duration, Chord>> chords = composition.chordList();
        assertEquals(1, chords.size());
        assertEquals(Duration.ZERO, chords.get(0).getKey());
        assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
        assertEquals(note2, chords.get(0).getValue().getSingleNote(1));
    }

    @Test
    public void testAdd_TwoNotes_TwoChord() {
        Composition composition = new Composition();
        Note note1 = TestMusicCreator.quarterNote(1);
        Note note2 = TestMusicCreator.quarterNote(2);
        composition.addNote(Duration.ZERO, note1, 0);
        composition.addNote(Duration.QUARTER_BEAT, note2, 0);
        List<Map.Entry<Duration, Chord>> chords = composition.chordList();
        assertEquals(2, chords.size());
        assertEquals(Duration.ZERO, chords.get(0).getKey());
        assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
        assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
        assertEquals(note2, chords.get(1).getValue().getSingleNote(0));
    }

    @Test
    public void testAdd_OverlapBefore() {
        Composition composition = new Composition();
        Note note1 = TestMusicCreator.halfNote(1);
        Note note2 = TestMusicCreator.quarterNote(2);
        composition.addNote(Duration.ZERO, note1, 0);
        composition.addNote(Duration.QUARTER_BEAT, note2, 0);
        List<Map.Entry<Duration, Chord>> chords = composition.chordList();
        assertEquals(2, chords.size());
        assertEquals(Duration.ZERO, chords.get(0).getKey());
        assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
        assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
        assertEquals(note2, chords.get(1).getValue().getSingleNote(0));
    }

    @Test
    public void testAdd_OverlapAfter() {
        Composition composition = new Composition();
        Note note1 = TestMusicCreator.quarterNote(1);
        Note note2 = TestMusicCreator.halfNote(2);
        composition.addNote(Duration.QUARTER_BEAT, note1, 0);
        composition.addNote(Duration.ZERO, note2, 0);
        List<Map.Entry<Duration, Chord>> chords = composition.chordList();
        assertEquals(2, chords.size());
        assertEquals(Duration.ZERO, chords.get(0).getKey());
        assertEquals(note2, chords.get(0).getValue().getSingleNote(0));
        assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
        assertEquals(note1, chords.get(1).getValue().getSingleNote(0));
    }

    @Test
    public void testCompositionDuration() {
        assertEquals(new Duration(8, 1), TestMusicCreator.newWholeNoteScale().totalDuration());
    }

    @Test
    public void testCompositionLength() {
        Composition wholeNoteScale = TestMusicCreator.newWholeNoteScale();
        Duration noteStart = new Duration(4, 1);
        Duration duration = new Duration(20, 1);
        wholeNoteScale.addNote(noteStart, new Note(Note.A8, duration), 2);
        assertEquals(noteStart.add(duration), wholeNoteScale.totalDuration());
    }
}
