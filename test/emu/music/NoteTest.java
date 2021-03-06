package emu.music;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {
    @Test
    public void testEquals() {
        assertTrue(new Note(1, new Duration(1, 2)).equals(new Note(1, new Duration(2, 4))));
        assertTrue(!new Note(1, new Duration(1, 2)).equals(new Note(Note.REST, new Duration(2, 4))));
    }
}
