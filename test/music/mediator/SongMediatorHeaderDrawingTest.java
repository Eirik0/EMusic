package music.mediator;

import static org.junit.Assert.assertTrue;
import main.EMusic;
import music.Song;
import music.mediator.drawer.MockLine;
import music.mediator.drawer.MockRectangle;
import music.mediator.drawer.MockString;
import music.mediator.drawer.TestDrawer;

import org.junit.Test;

public class SongMediatorHeaderDrawingTest {
	@Test
	public void testDrawColumnHeader_Bar() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 224, new TestDrawer());
		songMediator.setColumnHeaderUserInput(new TestUserInput(0, 0));
		songMediator.setHeaderDrawers(null, drawer);
		songMediator.drawColumnHeader();
		assertTrue("Bar 1", drawer.lines.contains(new MockLine(0, 16, 0, EMusic.HEADER_HEIGHT, DrawerHelper.BAR_COLOR)));
		assertTrue("Bar 2", drawer.lines.contains(new MockLine(256, 16, 256, EMusic.HEADER_HEIGHT, DrawerHelper.BAR_COLOR)));
	}

	@Test
	public void testDrawColumnHeader_BarNumber() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 224, new TestDrawer());
		songMediator.setColumnHeaderUserInput(new TestUserInput(0, 0));
		songMediator.setHeaderDrawers(null, drawer);
		songMediator.drawColumnHeader();
		assertTrue("1", drawer.strings.contains(new MockString(0, 6, "1", DrawerHelper.TEXT_COLOR)));
		assertTrue("2", drawer.strings.contains(new MockString(256, 6, "2", DrawerHelper.TEXT_COLOR)));
	}

	@Test
	public void testDrawColumnHeaderMouse() {
		TestDrawer drawer = new TestDrawer();
		TestUserInput userInput = new TestUserInput(100, 0);
		DrawerHelper.drawColumnHeaderMouse(drawer, userInput);
		assertTrue(drawer.lines.contains(new MockLine(100, EMusic.HEADER_HEIGHT / 2, 100, EMusic.HEADER_HEIGHT, DrawerHelper.BEAT_COLOR)));
	}

	@Test
	public void testDrawRowHeader() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 100, new TestDrawer());
		songMediator.setHeaderDrawers(drawer, null);
		songMediator.drawRowHeader();
		assertTrue("C", drawer.rectangles.contains(new MockRectangle(0, 0, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("B", drawer.rectangles.contains(new MockRectangle(0, 16, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("A#", drawer.rectangles.contains(new MockRectangle(0, 32, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)));
		assertTrue("A", drawer.rectangles.contains(new MockRectangle(0, 48, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("G#", drawer.rectangles.contains(new MockRectangle(0, 64, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)));
		assertTrue("G", drawer.rectangles.contains(new MockRectangle(0, 80, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)));
		assertTrue("F#", drawer.rectangles.contains(new MockRectangle(0, 96, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)));
	}

	@Test
	public void testDrawRowHeader_DrawC() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 100, new TestDrawer());
		songMediator.setHeaderDrawers(drawer, null);
		songMediator.drawRowHeader();
		assertTrue("C9", drawer.strings.contains(new MockString(8, 8 - 3, "C9", DrawerHelper.TEXT_COLOR)));
	}

	@Test
	public void testDrawRowHeader_DrawCOffset() {
		TestDrawer drawer = new TestDrawer();
		SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 8, 300, 100, new TestDrawer());
		songMediator.setHeaderDrawers(drawer, null);
		songMediator.drawRowHeader();
		assertTrue("C9", drawer.strings.contains(new MockString(8, 0 - 3, "C9", DrawerHelper.TEXT_COLOR)));
	}

}
