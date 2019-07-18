package emu.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import emu.main.EMusic;
import emu.music.Duration;
import emu.music.mediator.CompositionMediator;

@SuppressWarnings("serial")
public class TimeSignatureColumnHeader extends JComponent {
    private final CompositionMediator compositionMediator;

    private Duration playerStart = Duration.ZERO;

    public TimeSignatureColumnHeader(CompositionMediator compositionMediator) {
        this.compositionMediator = compositionMediator;
        setPreferredSize(new Dimension(0, EMusic.HEADER_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        compositionMediator.drawColumnHeader();
        compositionMediator.drawColumnHeaderOn(g);
    }

    public Duration getPlayerStart() {
        return playerStart;
    }

    public void setPlayerStart(Duration playerStart) {
        this.playerStart = playerStart;
    }
}
