package music;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SongTest {
	@Test
	public void testAdd_OneNote_OneChord() {
		Song song = new Song();
		song.addNote(Duration.ZERO, TestMusicCreator.QUARTER_NOTE, 0);
		List<Map.Entry<Duration, Chord>> chords = song.chordList();
		assertEquals(1, chords.size());
		assertEquals(Duration.ZERO, chords.get(0).getKey());
		assertEquals(TestMusicCreator.QUARTER_NOTE, chords.get(0).getValue().getSingleNote(0));
	}

	@Test
	public void testAdd_TwoNotes_OneChord() {
		Song song = new Song();
		Note note1 = TestMusicCreator.quarterNote(1);
		Note note2 = TestMusicCreator.quarterNote(2);
		song.addNote(Duration.ZERO, note1, 0);
		song.addNote(Duration.ZERO, note2, 1);
		List<Map.Entry<Duration, Chord>> chords = song.chordList();
		assertEquals(1, chords.size());
		assertEquals(Duration.ZERO, chords.get(0).getKey());
		assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
		assertEquals(note2, chords.get(0).getValue().getSingleNote(1));
	}

	@Test
	public void testAdd_TwoNotes_TwoChord() {
		Song song = new Song();
		Note note1 = TestMusicCreator.quarterNote(1);
		Note note2 = TestMusicCreator.quarterNote(2);
		song.addNote(Duration.ZERO, note1, 0);
		song.addNote(Duration.QUARTER_BEAT, note2, 0);
		List<Map.Entry<Duration, Chord>> chords = song.chordList();
		assertEquals(2, chords.size());
		assertEquals(Duration.ZERO, chords.get(0).getKey());
		assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
		assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
		assertEquals(note2, chords.get(1).getValue().getSingleNote(0));
	}

	@Test
	public void testAdd_OverlapBefore() {
		Song song = new Song();
		Note note1 = TestMusicCreator.halfNote(1);
		Note note2 = TestMusicCreator.quarterNote(2);
		song.addNote(Duration.ZERO, note1, 0);
		song.addNote(Duration.QUARTER_BEAT, note2, 0);
		List<Map.Entry<Duration, Chord>> chords = song.chordList();
		assertEquals(2, chords.size());
		assertEquals(Duration.ZERO, chords.get(0).getKey());
		assertEquals(note1, chords.get(0).getValue().getSingleNote(0));
		assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
		assertEquals(note2, chords.get(1).getValue().getSingleNote(0));
	}

	@Test
	public void testAdd_OverlapAfter() {
		Song song = new Song();
		Note note1 = TestMusicCreator.quarterNote(1);
		Note note2 = TestMusicCreator.halfNote(2);
		song.addNote(Duration.QUARTER_BEAT, note1, 0);
		song.addNote(Duration.ZERO, note2, 0);
		List<Map.Entry<Duration, Chord>> chords = song.chordList();
		assertEquals(2, chords.size());
		assertEquals(Duration.ZERO, chords.get(0).getKey());
		assertEquals(note2, chords.get(0).getValue().getSingleNote(0));
		assertEquals(Duration.QUARTER_BEAT, chords.get(1).getKey());
		assertEquals(note1, chords.get(1).getValue().getSingleNote(0));
	}

	@Test
	public void testSongDuration() {
		assertEquals(new Duration(8, 1), TestMusicCreator.newWholeNoteScale().totalDuration());
	}

	@Test
	public void testSongLength() {
		Song wholeNoteScale = TestMusicCreator.newWholeNoteScale();
		Duration noteStart = new Duration(4, 1);
		Duration duration = new Duration(20, 1);
		wholeNoteScale.addNote(noteStart, new Note(Note.A8, duration), 2);
		assertEquals(noteStart.add(duration), wholeNoteScale.totalDuration());
	}
}
