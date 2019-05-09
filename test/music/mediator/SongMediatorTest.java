package music.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gui.mouse.SongMouseAdapter;

import java.util.List;
import java.util.Map.Entry;

import music.Chord;
import music.Duration;
import music.Note;
import music.Song;
import music.TestMusicCreator;
import music.mediator.drawer.MockLine;
import music.mediator.drawer.MockRectangle;
import music.mediator.drawer.TestDrawer;
import music.properties.NoteDimension;

import org.junit.Test;

public class SongMediatorTest {
	// Scrollable size
	@Test
	public void testGetScrollableWidth_MinimumWidth() {
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 100, 100);
		int scrollableWidth = songMediator.getScrollableWidth();
		assertEquals(2 * 100, scrollableWidth);
	}

	@Test
	public void testGetScrollableWidth_BeyondMinimum() {
		SongMediator songMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 0, 0);
		int scrollableWidth = songMediator.getScrollableWidth();
		double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16; // = 2048 (8 whole notes)
		assertEquals(expectedWidth, scrollableWidth, 0.000001);
	}

	@Test
	public void testGetScrollableWidth_ExtraScreenSmall() {
		SongMediator songMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 100, 0);
		int scrollableWidth = songMediator.getScrollableWidth();
		double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16 + 100;
		assertEquals(expectedWidth, scrollableWidth, 0.000001);
	}

	@Test
	public void testGetScrollableWidth_ExtraScreenLarge() {
		SongMediator songMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 2000, 0);
		int scrollableWidth = songMediator.getScrollableWidth();
		double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16 + 2000; // 8 whole notes
		assertEquals(expectedWidth, scrollableWidth, 0.000001);
	}

	@Test
	public void testGetScrollableHeight() {
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0);
		int scrollableHeight = songMediator.getScrollableHeight();
		assertEquals(NoteDimension.DEFAULT_SIXTEENTH_HEIGHT * NoteDimension.TOTAL_NOTES, scrollableHeight, 0.000001);
	}

	// Draw background
	@Test
	public void testDrawKeys() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 160, 224, drawer);
		songMediator.drawBackground();
		assertTrue("C", drawer.rectangles.contains(new MockRectangle(0, 0, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("B", drawer.rectangles.contains(new MockRectangle(0, 16, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("A#", drawer.rectangles.contains(new MockRectangle(0, 32, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("A", drawer.rectangles.contains(new MockRectangle(0, 48, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("G#", drawer.rectangles.contains(new MockRectangle(0, 64, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("G", drawer.rectangles.contains(new MockRectangle(0, 80, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("F#", drawer.rectangles.contains(new MockRectangle(0, 96, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("F", drawer.rectangles.contains(new MockRectangle(0, 112, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("E", drawer.rectangles.contains(new MockRectangle(0, 128, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("D#", drawer.rectangles.contains(new MockRectangle(0, 144, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("D", drawer.rectangles.contains(new MockRectangle(0, 160, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("C#", drawer.rectangles.contains(new MockRectangle(0, 176, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("C2", drawer.rectangles.contains(new MockRectangle(0, 192, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("B2", drawer.rectangles.contains(new MockRectangle(0, 208, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
	}

	@Test
	public void testDrawKeys_ScrollDownSlightly() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 8, 160, 224, drawer);
		songMediator.drawBackground();
		assertTrue("C", drawer.rectangles.contains(new MockRectangle(0, -8, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("B", drawer.rectangles.contains(new MockRectangle(0, 8, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("A#", drawer.rectangles.contains(new MockRectangle(0, 24, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("A", drawer.rectangles.contains(new MockRectangle(0, 40, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("G#", drawer.rectangles.contains(new MockRectangle(0, 56, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("G", drawer.rectangles.contains(new MockRectangle(0, 72, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("F#", drawer.rectangles.contains(new MockRectangle(0, 88, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("F", drawer.rectangles.contains(new MockRectangle(0, 104, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("E", drawer.rectangles.contains(new MockRectangle(0, 120, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("D#", drawer.rectangles.contains(new MockRectangle(0, 136, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("D", drawer.rectangles.contains(new MockRectangle(0, 152, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("C#", drawer.rectangles.contains(new MockRectangle(0, 168, 159, 15, DrawerHelper.SHARP_COLOR, true)));
		assertTrue("C2", drawer.rectangles.contains(new MockRectangle(0, 184, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("B2", drawer.rectangles.contains(new MockRectangle(0, 200, 159, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("A#2", drawer.rectangles.contains(new MockRectangle(0, 216, 159, 15, DrawerHelper.SHARP_COLOR, true)));
	}

	// Draw bars
	@Test
	public void testDrawBars() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 1000, 224, drawer);
		songMediator.drawBars();
		assertTrue("Bar 1", drawer.lines.contains(new MockLine(0, 0, 0, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 2", drawer.lines.contains(new MockLine(256, 0, 256, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 3", drawer.lines.contains(new MockLine(512, 0, 512, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 4", drawer.lines.contains(new MockLine(768, 0, 768, 224, DrawerHelper.BAR_COLOR)));
	}

	@Test
	public void testDrawBars_Offset() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 25, 8, 1000, 224, drawer);
		songMediator.drawBars();
		assertTrue("Bar 2", drawer.lines.contains(new MockLine(231, 0, 231, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 3", drawer.lines.contains(new MockLine(487, 0, 487, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 4", drawer.lines.contains(new MockLine(743, 0, 743, 224, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 5", drawer.lines.contains(new MockLine(999, 0, 999, 224, DrawerHelper.BAR_COLOR)));
	}

	@Test
	public void testDrawNoteDivisions() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 250, 224, drawer);
		songMediator.drawBars();
		assertTrue("Division 1", drawer.lines.contains(new MockLine(0, 0, 0, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)));
		assertTrue("Division 2", drawer.lines.contains(new MockLine(64, 0, 64, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)));
		assertTrue("Division 3", drawer.lines.contains(new MockLine(128, 0, 128, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)));
		assertTrue("Division 4", drawer.lines.contains(new MockLine(192, 0, 192, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)));
	}

	// Draw song
	@Test
	public void testDrawSong() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 0, 0, 2000, 224, drawer);
		songMediator.drawSong();

		assertTrue("C8", drawer.rectangles.contains(new MockRectangle(1, 193, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("D8", drawer.rectangles.contains(new MockRectangle(257, 161, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("E8", drawer.rectangles.contains(new MockRectangle(513, 129, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("F8", drawer.rectangles.contains(new MockRectangle(769, 113, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("G8", drawer.rectangles.contains(new MockRectangle(1025, 81, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("A8", drawer.rectangles.contains(new MockRectangle(1281, 49, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("B8", drawer.rectangles.contains(new MockRectangle(1537, 17, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("C9", drawer.rectangles.contains(new MockRectangle(1793, 1, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
	}

	@Test
	public void testDrawSong_Offset() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 100, 100, 2000, 224, drawer);
		songMediator.drawSong();

		assertTrue("C8", drawer.rectangles.contains(new MockRectangle(-99, 93, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("D8", drawer.rectangles.contains(new MockRectangle(157, 61, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("E8", drawer.rectangles.contains(new MockRectangle(413, 29, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
		assertTrue("F8", drawer.rectangles.contains(new MockRectangle(669, 13, 254, 14, DrawerHelper.NOTE_COLORS[1], true)));
	}

	// Draw state
	@Test
	public void testDrawAddingSecondNoteWhileDragging() {
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(10, 0);
		Song song = new Song();
		TestSongProperties testSongProperties = new TestSongProperties();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, testSongProperties, drawer, testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		testSongProperties.selectedVoice = 7;
		testInput.setMouseXY(10, 20);
		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		testInput.setMouseXY(70, 20);
		songMediator.drawState();

		assertNull(song.chordList().get(0).getValue().getSingleNote(7));
		assertTrue(drawer.rectangles.contains(new MockRectangle(1, 17, 126, 14, DrawerHelper.NOTE_COLORS[7], true)));
	}

	@Test
	public void testDrawMouse() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(20, 20);
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 160, 224, testProperties, drawer, testInput);

		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(17, 17, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
	}

	@Test
	public void testDrawMouse_Offset() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(20, 20);
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 8, 8, 160, 224, testProperties, drawer, testInput);

		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(9, 9, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));

		drawer.clear();
		testInput.setMouseXY(25, 25);
		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(25, 25, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
	}

	@Test
	public void testDrawMouse_VerticalShift() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(0, 67);
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 160, 224, testProperties, drawer, testInput);

		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(1, 65, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
	}

	@Test
	public void testDrawMouse_SmallDivisions() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.getTimeSignature().setNoteDuration(new Duration(1, 8));
		testProperties.getTimeSignature().setDivision(new Duration(1, 3));
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(112, 0);
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 224, testProperties, drawer, testInput);

		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(108, 1, 8, 14, DrawerHelper.NOTE_COLORS[0], true)));

		drawer.clear();
		testInput.setMouseXY(122, 0);
		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(118, 1, 9, 14, DrawerHelper.NOTE_COLORS[0], true)));

		drawer.clear();
		testInput.setMouseXY(133, 0);
		songMediator.drawState();
		assertTrue(drawer.rectangles.contains(new MockRectangle(129, 1, 9, 14, DrawerHelper.NOTE_COLORS[0], true)));
	}

	@Test
	public void testDrawMouse_MouseExited() {
		TestDrawer drawer = new TestDrawer();
		TestUserInput testInput = new TestUserInput(0, 0);
		testInput.isMouseEntered = false;
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 160, 224, drawer, testInput);

		songMediator.drawState();
		assertTrue(drawer.rectangles.isEmpty());
	}

	// Add note
	@Test
	public void testAddNote() {
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(Duration.ZERO, chordDuration.getKey());
		assertEquals(new Note(1, new Duration(1, 4)), chordDuration.getValue().getSingleNote(0));
	}

	@Test
	public void testAddNote_Offset() {
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 200, 200, 160, 224, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(new Duration(3, 4), chordDuration.getKey());
		assertEquals(new Note(13, new Duration(1, 4)), chordDuration.getValue().getSingleNote(0));
	}

	@Test
	public void testAddNote_Triplet_Offset() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.getTimeSignature().setNoteDuration(new Duration(1, 8));
		testProperties.getTimeSignature().setDivision(new Duration(1, 3));
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 200, 200, 160, 224, testProperties, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(new Duration(19, 24), chordDuration.getKey());
		assertEquals(new Note(13, new Duration(1, 24)), chordDuration.getValue().getSingleNote(0));
	}

	@Test
	public void testAddNote_Voice2() {
		TestSongProperties testProperties = new TestSongProperties();
		testProperties.selectedVoice = 2;
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, testProperties, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(Duration.ZERO, chordDuration.getKey());
		assertEquals(new Note(1, new Duration(1, 4)), chordDuration.getValue().getSingleNote(2));
	}

	@Test
	public void testAddNoteWithDrag() {
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, new TestDrawer(), testInput);
		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		testInput.setMouseXY(70, 20);
		songMediator.mouseReleased();

		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(Duration.ZERO, chordDuration.getKey());
		assertEquals(new Note(1, new Duration(1, 2)), chordDuration.getValue().getSingleNote(0));
		assertEquals(0, chordDuration.getValue().getNotes()[0].getAdditionalNotes().size());
	}

	@Test
	public void testAddTwoNotesWithDrag() {
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		testInput.setMouseXY(70, 20);
		songMediator.mouseReleased();

		testInput.setMouseXY(10, 40);
		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		testInput.setMouseXY(70, 40);
		songMediator.mouseReleased();

		assertEquals(1, song.chordList().size());
		Entry<Duration, Chord> chordDuration = song.chordList().get(0);
		assertEquals(Duration.ZERO, chordDuration.getKey());
		assertEquals(new Note(1, new Duration(1, 2)), chordDuration.getValue().getSingleNote(0));
		List<Note> additionalNotes = chordDuration.getValue().getNotes()[0].getAdditionalNotes();
		assertEquals(1, additionalNotes.size());
		assertEquals(new Note(2, new Duration(1, 2)), additionalNotes.get(0));
	}

	// Remove note
	@Test
	public void testRemoveNote() {
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, new TestDrawer(), testInput);

		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();
		songMediator.mousePressed(SongMouseAdapter.RIGHT_CLICK);

		assertEquals(0, song.chordList().size());
	}

	@Test
	public void testRemoveTwoNotes() {
		TestSongProperties testProperties = new TestSongProperties();
		TestUserInput testInput = new TestUserInput(10, 20);
		Song song = new Song();
		SongMediator songMediator = TestMediatorCreator.newMediator(song, 0, 0, 160, 224, testProperties, new TestDrawer(), testInput);

		testInput.setMouseXY(20, 20);
		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		testProperties.selectedVoice = 1;
		testInput.setMouseXY(20, 40);
		songMediator.mousePressed(SongMouseAdapter.LEFT_CLICK);
		songMediator.mouseReleased();

		testInput.setMouseXY(20, 20);
		songMediator.mousePressed(SongMouseAdapter.RIGHT_CLICK);

		testInput.setMouseXY(20, 40);
		songMediator.mousePressed(SongMouseAdapter.RIGHT_CLICK);

		assertEquals(0, song.chordList().size());
	}
}
