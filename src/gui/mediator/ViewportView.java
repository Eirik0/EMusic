package gui.mediator;

import gui.PianoKeyRowHeader;
import gui.TimeSignatureColumnHeader;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import music.mediator.ISongView;

public class ViewportView implements ISongView {
    private final JViewport viewport;
    private final PianoKeyRowHeader pianoKeyRowHeader;
    private final TimeSignatureColumnHeader timeSignatureColumnHeader;

    public ViewportView(JViewport viewport, PianoKeyRowHeader pianoKeyRowHeader, TimeSignatureColumnHeader timeSignatureColumnHeader) {
        this.viewport = viewport;
        this.pianoKeyRowHeader = pianoKeyRowHeader;
        this.timeSignatureColumnHeader = timeSignatureColumnHeader;
    }

    @Override
    public int getX0() {
        return viewport.getViewPosition().x;
    }

    @Override
    public int getY0() {
        return viewport.getViewPosition().y;
    }

    @Override
    public int getWidth() {
        return viewport.getViewRect().width;
    }

    @Override
    public int getHeight() {
        return viewport.getViewRect().height;
    }

    @Override
    public void setSize(int width, int height) {
        viewport.getView().setPreferredSize(new Dimension(width, height));
        repaintAll();
        viewport.revalidate();
    }

    @Override
    public void setPosition(int x, int y) {
        SwingUtilities.invokeLater(() -> {
            repaintAll();
            viewport.setViewPosition(new Point(x, y));
        });
    }

    @Override
    public void repaint() {
        repaintAll();
    }

    private void repaintAll() {
        viewport.getView().repaint();
        pianoKeyRowHeader.repaint();
        timeSignatureColumnHeader.repaint();
    }
}
