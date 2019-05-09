package gui.mediator;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import music.mediator.IDrawer;
import util.EMath;

public class GraphicsDrawer implements IDrawer {
	private BufferedImage image;
	private Graphics2D g;

	public GraphicsDrawer() {
		createNewImage(10, 10);
	}

	private void createNewImage(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		g.setFont(g.getFont().deriveFont(10.0f));
	}

	@Override
	public void setComponentSize(int width, int height) {
		if ((width > 0 && width != image.getWidth()) || (height > 0 && height != image.getHeight())) {
			createNewImage(width, height);
		}
	}

	@Override
	public void setColor(Color color) {
		g.setColor(color);
	}

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		g.drawLine(x0, y0, x1, y1);
	}

	@Override
	public void drawRect(int x0, int y0, int width, int height) {
		g.drawRect(x0, y0, width - 1, height - 1);
	}

	@Override
	public void fillRect(int x0, int y0, int width, int height) {
		g.fillRect(x0, y0, width, height);
	}

	@Override
	public void drawCenteredString(int x0, int y0, String text) {
		FontMetrics metrics = g.getFontMetrics();
		double height = metrics.getHeight();
		double width = metrics.stringWidth(text);
		g.drawString(text, EMath.round(x0 - width / 2) + 1, EMath.round(y0 + height / 2));
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}
}
