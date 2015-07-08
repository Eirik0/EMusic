package music.mediator;

import java.awt.Graphics;
import java.io.File;

import jmusic.JMusicPlayer;
import music.Duration;
import music.Song;
import music.mediator.state.IMediatorState;
import music.mediator.state.MediatorStateFactory;
import util.EMath;

public class SongMediator {
	private final IDrawer songDrawer;
	private final ISongPlayer player;
	private final ITimer timer;

	private IDrawer rowHeaderDrawer;

	private IDrawer columnHeaderDrawer;
	private IUserInput columnHeaderUserInput;

	private ISongView view;
	private ISongProperties songProperties;
	private IUserInput userInput;

	private IMediatorState state; // set may be called multiple times

	private Song song;

	public SongMediator(IDrawer drawer, ISongPlayer player, ITimer timer) {
		songDrawer = drawer;
		this.player = player;
		this.timer = timer;

		song = new Song();

		MediatorStateFactory.updateReferences(songDrawer, this.player, this.timer);
	}

	// Initialization
	public void setSong(Song song) {
		this.song = song;
		state.setSong(song);
	}

	public void setView(ISongView view) {
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

	public void setSongProperties(ISongProperties songProperties) {
		this.songProperties = songProperties;
		MediatorStateFactory.updateReferences(this.songProperties);
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
		state.setSong(song);
	}

	public void setPlayerStartFromHeader(boolean backToStart) {
		if (backToStart) {
			songProperties.setPlayerStart(Duration.ZERO);
		} else {
			int x = view.getX0() + columnHeaderUserInput.getMouseX();
			double d = x / (songProperties.getNoteDimension().getSixteenthNoteWidth() * 16);
			songProperties.setPlayerStart(EMath.guessDuration(d));
		}
	}

	public void loadSongFromFile(File file) {
		setSong(JMusicPlayer.songFromFile(file, songProperties));
		view.setPosition(0, view.getY0());
		resizeView();
	}

	public void saveSongToFile(File file) {
		JMusicPlayer.songToFile(file, song, songProperties);
	}

	public void resizeView() {
		view.setSize(getScrollableWidth(), getScrollableHeight());
	}

	public void repaintView() {
		view.repaint();
	}

	// Size
	public int getScrollableWidth() {
		int songDurationInPixels = (int) songProperties.getNoteDimension().durationInPixels(song.totalDuration());
		return Math.max(2 * view.getWidth(), songDurationInPixels + view.getWidth());
	}

	public int getScrollableHeight() {
		return songProperties.getNoteDimension().songHeightInPixels();
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
		DrawerHelper.drawBackground(songDrawer, view, songProperties);
	}

	public void drawBars() {
		DrawerHelper.drawBars(songDrawer, view, songProperties);
	}

	public void drawSong() {
		DrawerHelper.drawSong(songDrawer, view, songProperties, song);
	}

	public void drawState() {
		state.draw();
	}

	public void drawSongOn(Graphics g) {
		g.drawImage(songDrawer.getImage(), view.getX0(), view.getY0(), null);
	}

	// Drawing headers
	public void drawRowHeader() {
		DrawerHelper.drawRowHeader(rowHeaderDrawer, view, songProperties);
	}

	public void drawRowHeaderOn(Graphics g) {
		g.drawImage(rowHeaderDrawer.getImage(), 0, 0, null);
	}

	public void drawColumnHeader() {
		DrawerHelper.drawColumnHeader(columnHeaderDrawer, view, songProperties, columnHeaderUserInput);
	}

	public void drawColumnHeaderOn(Graphics g) {
		g.drawImage(columnHeaderDrawer.getImage(), 0, 0, null);
	}
}
