package gui.mouse;

import gui.TimeSignatureColumnHeader;
import music.mediator.SongMediator;

public class HeaderMouseController implements IMouseController {
	private final SongMediator songMediator;
	private final TimeSignatureColumnHeader header;

	public HeaderMouseController(SongMediator songMediator, TimeSignatureColumnHeader timeSignatureColumnHeader) {
		this.songMediator = songMediator;
		header = timeSignatureColumnHeader;
	}

	@Override
	public void onMousePressed(int button) {
		header.setPlayerStart(songMediator.calculatePlayerStart());
	}

	@Override
	public void onMouseReleased() {
	}

	@Override
	public void onSetMouseXY() {
		header.repaint();
	}
}
