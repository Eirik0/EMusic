package gui.mediator;

import music.mediator.ITimer;

public class ThreadTimer implements ITimer {
	private TimerRunnable timer;

	@Override
	public void start() {
		if (timer != null) {
			timer.stopRequested = true;
		}
		timer = new TimerRunnable();
		new Thread(timer).start();
	}

	@Override
	public long getTimeElapsed() {
		return timer == null ? 0 : timer.timeElapsed;
	}

	@Override
	public void stop() {
		timer.stopRequested = true;
	}

	private static class TimerRunnable implements Runnable {
		private boolean stopRequested = false;
		private long timeElapsed = 0;

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			while (!stopRequested) {
				timeElapsed = System.currentTimeMillis() - startTime;
				try {
					Thread.sleep(0, 500000);
				} catch (Exception e) {
				}
			}
		}
	}
}
