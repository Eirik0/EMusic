package music.mediator.drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import music.mediator.IDrawer;

public class TestDrawer implements IDrawer {
	public int width;
	public int height;

	public Color color;

	public List<MockLine> lines = new ArrayList<>();
	public List<MockRectangle> rectangles = new ArrayList<>();
	public List<MockString> strings = new ArrayList<>();

	public void clear() {
		lines.clear();
		rectangles.clear();
		color = null;
	}

	@Override
	public void setComponentSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		lines.add(new MockLine(x0, y0, x1, y1, color));
	}

	@Override
	public void drawRect(int x0, int y0, int width, int height) {
		rectangles.add(new MockRectangle(x0, y0, width - 1, height - 1, color, false));
	}

	@Override
	public void fillRect(int x0, int y0, int width, int height) {
		rectangles.add(new MockRectangle(x0, y0, width - 1, height - 1, color, true));
	}

	@Override
	public void drawCenteredString(int x0, int y0, String text) {
		strings.add(new MockString(x0, y0, text, color));
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}