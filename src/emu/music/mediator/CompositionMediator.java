package emu.music.mediator;

import java.awt.Graphics;
import java.io.File;

import emu.jmusic.JMusicPlayer;
import emu.music.Duration;
import emu.music.Composition;
import emu.music.mediator.state.IMediatorState;
import emu.music.mediator.state.MediatorStateFactory;
import emu.util.EMath;

public class CompositionMediator {
    private final IDrawer compositionDrawer;
    private final ICompositionPlayer player;
    private final ITimer timer;

    private IDrawer rowHeaderDrawer;

    private IDrawer columnHeaderDrawer;
    private IUserInput columnHeaderUserInput;

    private ICompositionView view;
    private ICompositionProperties compositionProperties;
    private IUserInput userInput;

    private IMediatorState state; // set may be called multiple times

    private Composition composition;

    public CompositionMediator(IDrawer drawer, ICompositionPlayer player, ITimer timer) {
        compositionDrawer = drawer;
        this.player = player;
        this.timer = timer;

        composition = new Composition();

        MediatorStateFactory.updateReferences(compositionDrawer, this.player, this.timer);
    }

    // Initialization
    public void setComposition(Composition composition) {
        this.composition = composition;
        state.setComposition(composition);
    }

    public void setView(ICompositionView view) {
        this.view = view;
        MediatorStateFactory.updateReferences(this.view);
    }

    public void setHeaderDrawers(IDrawer rowHeaderDrawer, IDrawer columnHeaderDrawer) {
        this.rowHeaderDrawer = rowHeaderDrawer;
        this.columnHeaderDrawer = columnHeaderDrawer;
    }

    public void setColumnHeaderUserInput(IUserInput columnHeaderUserInput) {
        this.columnHeaderUserInput = columnHeaderUserInput;
    }

    public void setCompositionProperties(ICompositionProperties compositionProperties) {
        this.compositionProperties = compositionProperties;
        MediatorStateFactory.updateReferences(this.compositionProperties);
    }

    public void setUserInput(IUserInput userInput) {
        this.userInput = userInput;
        MediatorStateFactory.updateReferences(this.userInput);
    }

    // Control
    public void setState(Class<? extends IMediatorState> stateClass) {
        if (state != null) {
            state.finish();
        }
        state = MediatorStateFactory.newInstance(stateClass);
        state.setComposition(composition);
    }

    public void setPlayerStartFromHeader(boolean backToStart) {
        if (backToStart) {
            compositionProperties.setPlayerStart(Duration.ZERO);
        } else {
            int x = view.getX0() + columnHeaderUserInput.getMouseX();
            double d = x / (compositionProperties.getNoteDimension().getSixteenthNoteWidth() * 16);
            compositionProperties.setPlayerStart(EMath.guessDuration(d));
        }
    }

    public void loadCompositionFromFile(File file) {
        setComposition(JMusicPlayer.compositionFromFile(file, compositionProperties));
        view.setPosition(0, view.getY0());
        resizeView();
    }

    public void saveCompositionToFile(File file) {
        JMusicPlayer.compositionToFile(file, composition, compositionProperties);
    }

    public void resizeView() {
        view.setSize(getScrollableWidth(), getScrollableHeight());
    }

    public void repaintView() {
        view.repaint();
    }

    // Size
    public int getScrollableWidth() {
        int compositionDurationInPixels = (int) compositionProperties.getNoteDimension().durationInPixels(composition.totalDuration());
        return Math.max(2 * view.getWidth(), compositionDurationInPixels + view.getWidth());
    }

    public int getScrollableHeight() {
        return compositionProperties.getNoteDimension().compositionHeightInPixels();
    }

    // User input
    public void mousePressed(int button) {
        state.mousePressed(button);
    }

    public void mouseReleased() {
        state.mouseReleased();
    }

    // Drawing view
    public void drawBackground() {
        DrawerHelper.drawBackground(compositionDrawer, view, compositionProperties);
    }

    public void drawBars() {
        DrawerHelper.drawBars(compositionDrawer, view, compositionProperties);
    }

    public void drawComposition() {
        DrawerHelper.drawComposition(compositionDrawer, view, compositionProperties, composition);
    }

    public void drawState() {
        state.draw();
    }

    public void drawCompositionOn(Graphics g) {
        g.drawImage(compositionDrawer.getImage(), view.getX0(), view.getY0(), null);
    }

    // Drawing headers
    public void drawRowHeader() {
        DrawerHelper.drawRowHeader(rowHeaderDrawer, view, compositionProperties);
    }

    public void drawRowHeaderOn(Graphics g) {
        g.drawImage(rowHeaderDrawer.getImage(), 0, 0, null);
    }

    public void drawColumnHeader() {
        DrawerHelper.drawColumnHeader(columnHeaderDrawer, view, compositionProperties, columnHeaderUserInput);
    }

    public void drawColumnHeaderOn(Graphics g) {
        g.drawImage(columnHeaderDrawer.getImage(), 0, 0, null);
    }
}
