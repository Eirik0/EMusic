package gui.mouse;

import music.mediator.SongMediator;

public class ViewMouseController implements IMouseController {
	private final SongMediator songMediator;

	public ViewMouseController(SongMediator songMediator) {
		this.songMediator = songMediator;
	}

	@Override
	public void onMousePressed(int button) {
		songMediator.mousePressed(button);

	}

	@Override
	public void onMouseReleased() {
		songMediator.mouseReleased();
		songMediator.resizeView();
	}

	@Override
	public void onSetMouseXY() {
		songMediator.repaintView();
	}
}
