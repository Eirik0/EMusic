package gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.EMusic;
import music.Duration;
import music.mediator.SongMediator;

@SuppressWarnings("serial")
public class TimeSignatureColumnHeader extends JComponent {
	private final SongMediator songMediator;

	private Duration playerStart = Duration.ZERO;

	public TimeSignatureColumnHeader(SongMediator songMediator) {
		this.songMediator = songMediator;
		setPreferredSize(new Dimension(0, EMusic.HEADER_HEIGHT));
	}

	@Override
	protected void paintComponent(Graphics g) {
		songMediator.drawColumnHeader();
		songMediator.drawColumnHeaderOn(g);
	}

	public Duration getPlayerStart() {
		return playerStart;
	}

	public void setPlayerStart(Duration playerStart) {
		this.playerStart = playerStart;
	}
}
