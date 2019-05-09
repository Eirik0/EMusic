package music;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ChordTest {
    @Test
    public void testCreateChord() {
        Chord chord = TestMusicCreator.newChord();
        assertEquals(1, chord.numVoices());
    }

    @Test
    public void testAddNote() {
        Chord chord = TestMusicCreator.newChord();
        chord.addNote(TestMusicCreator.QUARTER_NOTE, 4);
        assertEquals(2, chord.numVoices());
    }

    @Test
    public void testAddTwoNotes_One() {
        Note note1 = TestMusicCreator.quarterNote(1);
        Note note2 = TestMusicCreator.quarterNote(2);
        Chord chord = new Chord(note1, 1);
        chord.addNote(note2, 1);
        assertEquals(note1, chord.getSingleNote(1));
        assertEquals(note2, chord.getNotes()[1].getAdditionalNotes().get(0));
    }

    @Test
    public void testGetNotes() {
        Note note = new Note(1, new Duration(1, 2));
        Chord chord = new Chord(note, 2);
        chord.addNote(TestMusicCreator.QUARTER_NOTE, 4);
        assertNull(chord.getSingleNote(0));
        assertNull(chord.getSingleNote(1));
        assertEquals(note, chord.getSingleNote(2));
        assertNull(chord.getSingleNote(3));
        assertEquals(TestMusicCreator.QUARTER_NOTE, chord.getSingleNote(4));
        assertNull(chord.getSingleNote(5));
    }

    @Test
    public void testDuration() {
        Chord chord = TestMusicCreator.newChord();
        chord.addNote(new Note(1, Duration.WHOLE_BEAT), 6);
        assertEquals(Duration.WHOLE_BEAT, chord.totalDuration());
    }
}
