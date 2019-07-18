package emu.music.mediator;

import emu.music.Composition;
import emu.music.mediator.CompositionMediator;
import emu.music.mediator.drawer.TestDrawer;
import emu.music.mediator.state.NoteEntryState;

public class TestMediatorCreator {
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;

    public static CompositionMediator newMediator(Composition composition, int width, int height) {
        return newMediator(composition, width, height, new TestDrawer());
    }

    public static CompositionMediator newMediator(Composition composition, int width, int height, TestDrawer testDrawer) {
        return newMediator(composition, 0, 0, width, height, testDrawer);
    }

    public static CompositionMediator newMediator(Composition composition, int x0, int y0, int width, int height, TestDrawer testDrawer) {
        return newMediator(composition, x0, y0, width, height, testDrawer, new TestUserInput(0, 0));
    }

    public static CompositionMediator newMediator(Composition composition, int x0, int y0, int width, int height, TestDrawer testDrawer, TestUserInput testInput) {
        return newMediator(composition, x0, y0, width, height, new TestCompositionProperties(), testDrawer, testInput);
    }

    public static CompositionMediator newMediator(Composition composition, int x0, int y0, int width, int height, TestCompositionProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput) {
        return newMediator(composition, x0, y0, width, height, testProperties, testDrawer, testInput, new TestTimer());
    }

    public static CompositionMediator newMediator(Composition composition, TestCompositionProperties testProperties, TestDrawer testDrawer, TestTimer timer) {
        return newMediator(composition, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static CompositionMediator newMediator(Composition composition, int x0, int y0, int width, int height, TestCompositionProperties testProperties, TestDrawer testDrawer,
            TestTimer timer) {
        return newMediator(composition, x0, y0, width, height, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static CompositionMediator newMediator(Composition composition, int x0, int y0, int width, int height, TestCompositionProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput, TestTimer timer) {
        return newMediator(composition, new TestView(x0, y0, width, height), testProperties, testDrawer, testInput, timer);
    }

    public static CompositionMediator newMediator(Composition composition, TestView view, TestCompositionProperties testProperties, TestDrawer testDrawer,
            TestTimer timer) {
        return newMediator(composition, view, testProperties, testDrawer, new TestUserInput(0, 0), timer);
    }

    public static CompositionMediator newMediator(Composition composition, TestView view, TestCompositionProperties testProperties, TestDrawer testDrawer,
            TestUserInput testInput, TestTimer timer) {
        CompositionMediator mediator = new CompositionMediator(testDrawer, new NoOpPlayer(), timer);
        mediator.setView(view);
        mediator.setCompositionProperties(testProperties);
        mediator.setUserInput(testInput);
        mediator.setState(NoteEntryState.class);
        mediator.setComposition(composition);
        return mediator;
    }
}
