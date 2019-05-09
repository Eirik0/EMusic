package music.properties;

public class DrawingOptions {
	private boolean drawKeys = true;
	private boolean drawBars = true;
	private boolean drawPlayingLine = true;

	public boolean shouldDrawKeys() {
		return drawKeys;
	}

	public boolean shouldDrawBars() {
		return drawBars;
	}

	public boolean shouldDrawPlayingLine() {
		return drawPlayingLine;
	}

	public void toggleDrawKeys() {
		drawKeys = !drawKeys;
	}

	public void toggleDrawBars() {
		drawBars = !drawBars;
	}

	public void toggleDrawPlayingLine() {
		drawPlayingLine = !drawPlayingLine;
	}
}
