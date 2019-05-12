package emu.music.mediator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import emu.main.EMusic;
import emu.music.Song;
import emu.music.mediator.drawer.MockLine;
import emu.music.mediator.drawer.MockRectangle;
import emu.music.mediator.drawer.MockString;
import emu.music.mediator.drawer.TestDrawer;

public class SongMediatorHeaderDrawingTest {
    @Test
    public void testDrawColumnHeader_Bar() {
        TestDrawer drawer = new TestDrawer();
        SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 224, new TestDrawer());
        songMediator.setColumnHeaderUserInput(new TestUserInput(0, 0));
        songMediator.setHeaderDrawers(null, drawer);
        songMediator.drawColumnHeader();
        assertTrue(drawer.lines.contains(new MockLine(0, 16, 0, EMusic.HEADER_HEIGHT, DrawerHelper.BAR_COLOR)), "Bar 1");
        assertTrue(drawer.lines.contains(new MockLine(256, 16, 256, EMusic.HEADER_HEIGHT, DrawerHelper.BAR_COLOR)), "Bar 2");
    }

    @Test
    public void testDrawColumnHeader_BarNumber() {
        TestDrawer drawer = new TestDrawer();
        SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 224, new TestDrawer());
        songMediator.setColumnHeaderUserInput(new TestUserInput(0, 0));
        songMediator.setHeaderDrawers(null, drawer);
        songMediator.drawColumnHeader();
        assertTrue(drawer.strings.contains(new MockString(0, 6, "1", DrawerHelper.TEXT_COLOR)), "1");
        assertTrue(drawer.strings.contains(new MockString(256, 6, "2", DrawerHelper.TEXT_COLOR)), "2");
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
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 0, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)), "C");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 16, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)), "B");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 32, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)), "A#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 48, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)), "A");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 64, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)), "G#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 80, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.NATURAL_COLOR, true)), "G");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 96, EMusic.HEADER_WIDTH - 1, 15, DrawerHelper.HEADER_SHARP_COLOR, true)), "F#");
    }

    @Test
    public void testDrawRowHeader_DrawC() {
        TestDrawer drawer = new TestDrawer();
        SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 0, 300, 100, new TestDrawer());
        songMediator.setHeaderDrawers(drawer, null);
        songMediator.drawRowHeader();
        assertTrue(drawer.strings.contains(new MockString(8, 8 - 3, "C9", DrawerHelper.TEXT_COLOR)), "C9");
    }

    @Test
    public void testDrawRowHeader_DrawCOffset() {
        TestDrawer drawer = new TestDrawer();
        SongMediator songMediator = TestMediatorCreator.newMediator(new Song(), 0, 8, 300, 100, new TestDrawer());
        songMediator.setHeaderDrawers(drawer, null);
        songMediator.drawRowHeader();
        assertTrue(drawer.strings.contains(new MockString(8, 0 - 3, "C9", DrawerHelper.TEXT_COLOR)), "C9");
    }

}
