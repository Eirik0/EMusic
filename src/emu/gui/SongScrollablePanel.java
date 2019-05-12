package emu.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import emu.music.mediator.SongMediator;

@SuppressWarnings("serial")
public class SongScrollablePanel extends JPanel implements Scrollable {
    private final SongMediator songMediator;

    public SongScrollablePanel(SongMediator songMediator) {
        this.songMediator = songMediator;
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
        songMediator.drawBackground();
        songMediator.drawBars();
        songMediator.drawSong();
        songMediator.drawState();
        songMediator.drawSongOn(g);
    }
}
