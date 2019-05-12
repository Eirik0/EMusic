package emu.music.mediator;

import emu.music.Song;
import emu.music.mediator.SongMediator;
import emu.music.mediator.drawer.TestDrawer;
import emu.music.mediator.state.NoteEntryState;

public class TestMediatorCreator {
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;

    public static SongMediator newMediator(Song song, int width, int height) {
        return newMediator(song, width, height, new TestDrawer());
    }

    public static SongMediator newMediator(Song song, int width, int height, TestDrawer testDrawer) {
        return newMediator(song, 0, 0, width, height, testDrawer);
    }

    public static SongMediator newMediator(Song song, int x0, int y0, int width, int height, TestDrawer testDrawer) {
        return newMediator(song, x0, y0, width, height, testDrawer, new TestUserInput(0, 0));
    }

    public static SongMediator newMediator(Song song, int x0, int y0, int width, int height, TestDrawer testDrawer, TestUserInput testInput) {
        return newMediator(song, x0, y0, width, height, new TestSongProperties(), testDrawer, testInput);
    }

    public static SongMediator newMediator(Song song, int x0, int y0, int width, int height, TestSongProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput) {
        return newMediator(song, x0, y0, width, height, testProperties, testDrawer, testInput, new TestTimer());
    }

    public static SongMediator newMediator(Song song, TestSongProperties testProperties, TestDrawer testDrawer, TestTimer timer) {
        return newMediator(song, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static SongMediator newMediator(Song song, int x0, int y0, int width, int height, TestSongProperties testProperties, TestDrawer testDrawer,
            TestTimer timer) {
        return newMediator(song, x0, y0, width, height, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static SongMediator newMediator(Song song, int x0, int y0, int width, int height, TestSongProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput, TestTimer timer) {
        return newMediator(song, new TestView(x0, y0, width, height), testProperties, testDrawer, testInput, timer);
    }

    public static SongMediator newMediator(Song song, TestView view, TestSongProperties testProperties, TestDrawer testDrawer,
            TestTimer timer) {
        return newMediator(song, view, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static SongMediator newMediator(Song song, TestView view, TestSongProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput, TestTimer timer) {
        SongMediator mediator = new SongMediator(testDrawer, new NoOpPlayer(), timer);
        mediator.setView(view);
        mediator.setSongProperties(testProperties);
        mediator.setUserInput(testInput);
        mediator.setState(NoteEntryState.class);
        mediator.setSong(song);
        return mediator;
    }
}
