package music.mediator.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import music.Chord;
import music.Duration;
import music.Note;
import music.Song;
import music.mediator.DrawerHelper;
import music.mediator.IDrawer;
import music.mediator.ISongPlayer;
import music.mediator.ISongProperties;
import music.mediator.ISongView;
import music.mediator.ITimer;
import util.EMath;

public class SongPlayingState implements IMediatorState {
	private final ISongView view;
	private final IDrawer drawer;
	private final ISongPlayer player;
	private final ITimer timer;
	private final ISongProperties songProperties;

	private SongPlayingRunnable songPlayingRunnable;

	public SongPlayingState(ISongView view, IDrawer drawer, ISongPlayer player, ITimer timer, ISongProperties songProperties) {
		this.view = view;
		this.drawer = drawer;
		this.player = player;
		this.timer = timer;
		this.songProperties = songProperties;
	}

	@Override
	public void setSong(Song song) {
		if (songPlayingRunnable != null) {
			songPlayingRunnable.stopRequested = true;
		}
		songPlayingRunnable = new SongPlayingRunnable(song);
		new Thread(songPlayingRunnable).start();
	}

	@Override
	public void mousePressed(int button) {
	}

	@Override
	public void mouseReleased() {
	}

	@Override
	public void draw() {
		int x = EMath.round(Math.min(view.getWidth() / 2, getPixelsElapsed() - view.getX0()));
		drawer.setColor(DrawerHelper.PLAYER_BAR_COLOR);
		drawer.drawLine(x, 0, x, view.getHeight());
	}

	private double getPixelsElapsed() {
		double pixelsPerQuarterNote = songProperties.getNoteDimension().getSixteenthNoteWidth() * 4;
		double beatsElapsed = getBeatsElapsed();
		double offset = songProperties.getNoteDimension().durationInPixels(songProperties.getPlayerStart());
		return beatsElapsed * pixelsPerQuarterNote + offset;
	}

	private double getBeatsElapsed() {
		return timer.getTimeElapsed() * songProperties.getTempo() / 60000.0; // minutes to millis
	}

	@Override
	public void finish() {
		if (songPlayingRunnable != null) {
			songPlayingRunnable.stopRequested = true;
			// play an "empty" chord to stop the player playing
			player.playChord(new Chord(new Note(0, Duration.ZERO), 0), songProperties);
		}
	}

	private class SongPlayingRunnable implements Runnable {
		private boolean stopRequested = false;
		private final Song song;

		public SongPlayingRunnable(Song song) {
			this.song = song;
		}

		@Override
		public void run() {
			Duration start = songProperties.getPlayerStart();
			ArrayList<Entry<Duration, Chord>> chordList = song.chordList();
			int i = 0;
			for (; !stopRequested && i < chordList.size() && chordList.get(i).getKey().compareTo(start) < 0; i++) {
				// skip notes before the requested start
			}
			if (i < chordList.size()) {
				List<Entry<Duration, Chord>> chords = new ArrayList<>();
				for (; !stopRequested && i < chordList.size(); ++i) {
					chords.add(chordList.get(i));
				}
				if (!stopRequested) {
					player.playChords(chords, songProperties);
					timer.start();
				}
			}

			Duration totalDuration = song.totalDuration();
			double pixelsElapsed = getPixelsElapsed();
			int lastX = Integer.MIN_VALUE;
			while (!stopRequested && pixelsElapsed < songProperties.getNoteDimension().durationInPixels(totalDuration)) {
				pixelsElapsed = getPixelsElapsed();
				int x = EMath.round(pixelsElapsed - (double) view.getWidth() / 2);
				if (x != lastX) {
					view.setPosition(Math.max(0, x), view.getY0());
					lastX = x;
				}
				try {
					Thread.sleep(0, 500000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
