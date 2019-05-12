package emu.music.mediator.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import emu.music.Song;
import emu.music.TestMusicCreator;
import emu.music.mediator.DrawerHelper;
import emu.music.mediator.SongMediator;
import emu.music.mediator.TestMediatorCreator;
import emu.music.mediator.TestSongProperties;
import emu.music.mediator.TestTimer;
import emu.music.mediator.TestUserInput;
import emu.music.mediator.TestView;
import emu.music.mediator.drawer.MockLine;
import emu.music.mediator.drawer.MockRectangle;
import emu.music.mediator.drawer.TestDrawer;

public class SongPlayingStateTest {
    @Test
    public void testDrawLine() {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        TestSongProperties properties = new TestSongProperties();
        properties.setTempo(60);
        SongMediator mediator = TestMediatorCreator.newMediator(song, properties, drawer, timer);
        mediator.setState(SongPlayingState.class);
        timer.timeElapsed = 500;
        mediator.drawState();
        assertTrue(drawer.lines.contains(new MockLine(32, 0, 32, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
    }

    @Test
    public void testDrawLine_Offset() {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        TestSongProperties properties = new TestSongProperties();
        properties.setTempo(60);
        SongMediator mediator = TestMediatorCreator.newMediator(song, 20, 0, 640, TestMediatorCreator.DEFAULT_HEIGHT, properties, drawer, timer);
        mediator.setState(SongPlayingState.class);
        timer.timeElapsed = 500;
        mediator.drawState();
        assertTrue(drawer.lines.contains(new MockLine(12, 0, 12, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
    }

    @Test
    public void testScrollWithSong() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(0, 0, 128, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        TestSongProperties properties = new TestSongProperties();
        properties.setTempo(60);
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, properties, drawer, timer);
        mediator.setState(SongPlayingState.class);
        timer.timeElapsed = 1500;
        Thread.sleep(50);
        mediator.drawState();
        assertTrue(drawer.lines.contains(new MockLine(64, 0, 64, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
        assertEquals(32, view.getX0());
    }

    @Test
    public void testPlayerStartPosition() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(0, 0, 128, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, new TestSongProperties(), drawer, timer);
        mediator.setColumnHeaderUserInput(new TestUserInput(32, 5));
        mediator.setPlayerStartFromHeader(false);
        mediator.setState(SongPlayingState.class);
        Thread.sleep(50);
        mediator.drawState();
        assertTrue(drawer.lines.contains(new MockLine(32, 0, 32, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
        assertEquals(0, view.getX0());
    }

    @Test
    public void testPlayerStartPosition_ScrolledForward_StartOnScreen() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(128, 0, 128, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, new TestSongProperties(), drawer, timer);
        mediator.setColumnHeaderUserInput(new TestUserInput(32, 5));
        mediator.setPlayerStartFromHeader(false);
        mediator.setState(SongPlayingState.class);
        Thread.sleep(50);
        mediator.drawState();
        MockLine expected = new MockLine(32, 0, 32, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR);
        assertTrue(drawer.lines.contains(expected), drawer.lines.get(0).toString());
        assertEquals(128, view.getX0());
    }

    @Test
    public void testPlayerStartPosition_ScrolledForward_JumpBack() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(0, 0, 128, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, new TestSongProperties(), drawer, timer);
        mediator.setColumnHeaderUserInput(new TestUserInput(96, 5));
        mediator.setPlayerStartFromHeader(false);
        view.setPosition(128, 0);
        mediator.setState(SongPlayingState.class);
        Thread.sleep(50);
        mediator.drawState();
        MockLine expected = new MockLine(0, 0, 0, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR);
        assertTrue(drawer.lines.contains(expected), drawer.lines.get(0).toString());
        assertEquals(96, view.getX0());
    }

    @Test
    public void testPlayerStartPosition_ScrolledForward_JumpForward() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(0, 0, 128, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, new TestSongProperties(), drawer, timer);
        mediator.setColumnHeaderUserInput(new TestUserInput(256, 5));
        mediator.setPlayerStartFromHeader(false);
        view.setPosition(0, 0);
        mediator.setState(SongPlayingState.class);
        Thread.sleep(50);
        mediator.drawState();
        MockLine expected = new MockLine(64, 0, 64, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR);
        assertTrue(drawer.lines.contains(expected), drawer.lines.get(0).toString());
        assertEquals(192, view.getX0());
    }

    @Test
    public void testHighlightPlayingNotes() throws InterruptedException {
        Song song = TestMusicCreator.newWholeNoteScale(0);
        TestView view = new TestView(0, 0, 2048, TestMediatorCreator.DEFAULT_HEIGHT);
        TestDrawer drawer = new TestDrawer();
        TestTimer timer = new TestTimer();
        TestSongProperties properties = new TestSongProperties();
        properties.setTempo(60);
        SongMediator mediator = TestMediatorCreator.newMediator(song, view, properties, drawer, timer);
        mediator.setState(SongPlayingState.class);
        timer.timeElapsed = 10000; // 640 pixels
        Thread.sleep(50);
        mediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(513, 129, 254, 14, DrawerHelper.inverse(DrawerHelper.NOTE_COLORS[0]), true)), "E8");
    }
}
