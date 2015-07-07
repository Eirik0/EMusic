package music.mediator.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import music.Song;
import music.TestMusicCreator;
import music.mediator.DrawerHelper;
import music.mediator.SongMediator;
import music.mediator.TestMediatorCreator;
import music.mediator.TestSongProperties;
import music.mediator.TestTimer;
import music.mediator.TestView;
import music.mediator.drawer.MockLine;
import music.mediator.drawer.TestDrawer;

import org.junit.Test;

public class SongPlayingStateTest {
	@Test
	public void testDrawLine() {
		Song song = TestMusicCreator.newWholeNoteScale(0);
		TestDrawer drawer = new TestDrawer();
		TestTimer timer = new TestTimer();
		TestSongProperties properties = new TestSongProperties();
		properties.setTempo(60);
		SongMediator mediator = TestMediatorCreator.newMediator(song, properties, drawer, timer);
		mediator.setState(SongPlayingState.class);
		timer.timeElapsed = 500;
		mediator.drawState();
		assertTrue(drawer.lines.contains(new MockLine(32, 0, 32, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
	}

	@Test
	public void testDrawLine_Offset() {
		Song song = TestMusicCreator.newWholeNoteScale(0);
		TestDrawer drawer = new TestDrawer();
		TestTimer timer = new TestTimer();
		TestSongProperties properties = new TestSongProperties();
		properties.setTempo(60);
		SongMediator mediator = TestMediatorCreator.newMediator(song, 20, 0, 640, 480, properties, drawer, timer);
		mediator.setState(SongPlayingState.class);
		timer.timeElapsed = 500;
		mediator.drawState();
		assertTrue(drawer.lines.contains(new MockLine(12, 0, 12, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
	}

	@Test
	public void testScrollWithSong() throws InterruptedException {
		Song song = TestMusicCreator.newWholeNoteScale(0);
		TestView view = new TestView(0, 0, 128, 480);
		TestDrawer drawer = new TestDrawer();
		TestTimer timer = new TestTimer();
		TestSongProperties properties = new TestSongProperties();
		properties.setTempo(60);
		SongMediator mediator = TestMediatorCreator.newMediator(song, view, properties, drawer, timer);
		mediator.setState(SongPlayingState.class);
		timer.timeElapsed = 1500;
		Thread.sleep(50);
		mediator.drawState();
		assertTrue(drawer.lines.contains(new MockLine(64, 0, 64, TestMediatorCreator.DEFAULT_HEIGHT, DrawerHelper.PLAYER_BAR_COLOR)));
		assertEquals(32, view.getX0());
	}
}
