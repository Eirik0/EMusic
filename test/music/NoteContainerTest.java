package music;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteContainerTest {
	@Test
	public void testAddNote() {
		Note note1 = TestMusicCreator.quarterNote(1);
		Note note2 = TestMusicCreator.quarterNote(2);
		NoteContainer noteContainer = new NoteContainer(note1);
		noteContainer.addNote(note2);
		assertEquals(1, noteContainer.getAdditionalNotes().size());
	}

	@Test
	public void testRemoveNote() {
		Note note1 = TestMusicCreator.quarterNote(1);
		Note note2 = TestMusicCreator.quarterNote(2);
		NoteContainer noteContainer = new NoteContainer(note1);
		noteContainer.addNote(note2);
		noteContainer.removeNote();
		assertEquals(note2, noteContainer.getNote());
		assertEquals(0, noteContainer.getAdditionalNotes().size());
	}

	@Test
	public void testRemoveSingleNote() {
		Note note1 = TestMusicCreator.quarterNote(1);
		NoteContainer noteContainer = new NoteContainer(note1);
		noteContainer.removeNote();
		assertTrue(noteContainer.isEmpty());
	}
}
