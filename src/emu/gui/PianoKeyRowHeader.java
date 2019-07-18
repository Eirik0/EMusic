package emu.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import emu.main.EMusic;
import emu.music.mediator.CompositionMediator;

@SuppressWarnings("serial")
public class PianoKeyRowHeader extends JComponent {
    private final CompositionMediator compositionMediator;

    public PianoKeyRowHeader(CompositionMediator compositionMediator) {
        this.compositionMediator = compositionMediator;
        setPreferredSize(new Dimension(EMusic.HEADER_WIDTH, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        compositionMediator.drawRowHeader();
        compositionMediator.drawRowHeaderOn(g);
    }
}
