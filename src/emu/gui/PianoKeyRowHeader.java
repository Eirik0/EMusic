package emu.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import emu.main.EMusic;
import emu.music.mediator.SongMediator;

@SuppressWarnings("serial")
public class PianoKeyRowHeader extends JComponent {
    private final SongMediator songMediator;

    public PianoKeyRowHeader(SongMediator songMediator) {
        this.songMediator = songMediator;
        setPreferredSize(new Dimension(EMusic.HEADER_WIDTH, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        songMediator.drawRowHeader();
        songMediator.drawRowHeaderOn(g);
    }
}
