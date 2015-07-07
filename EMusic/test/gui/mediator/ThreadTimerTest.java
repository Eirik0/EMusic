package gui.mediator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThreadTimerTest {
	private static final int SLEEP_TIME = 50;

	@Test
	public void testStop() throws InterruptedException {
		ThreadTimer timer = new ThreadTimer();
		timer.start();
		Thread.sleep(SLEEP_TIME);
		timer.stop();
		long timeElapsed = timer.getTimeElapsed();
		Thread.sleep(SLEEP_TIME);
		assertEquals(timeElapsed, timer.getTimeElapsed());
	}

	@Test
	public void testGetTimeElapsed() throws InterruptedException {
		ThreadTimer timer = new ThreadTimer();
		timer.start();
		Thread.sleep(SLEEP_TIME);
		long timeElapsed = timer.getTimeElapsed();
		timer.stop();
		assertTrue(String.valueOf(timeElapsed), timeElapsed > 0);
	}
}
