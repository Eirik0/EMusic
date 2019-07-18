package emu.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import emu.music.mediator.CompositionMediator;

@SuppressWarnings("serial")
public class CompositionScrollablePanel extends JPanel implements Scrollable {
    private final CompositionMediator compositionMediator;

    public CompositionScrollablePanel(CompositionMediator compositionMediator) {
        this.compositionMediator = compositionMediator;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        compositionMediator.drawBackground();
        compositionMediator.drawBars();
        compositionMediator.drawComposition();
        compositionMediator.drawState();
        compositionMediator.drawCompositionOn(g);
    }
}
