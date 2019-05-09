package music.mediator;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface IDrawer {
	public void setComponentSize(int width, int height);

	public void setColor(Color color);

	public void drawLine(int x0, int y0, int x1, int y1);

	public void drawRect(int x0, int y0, int width, int height);

	public void fillRect(int x0, int y0, int width, int height);

	public void drawCenteredString(int x0, int y0, String text);

	public BufferedImage getImage();
}
