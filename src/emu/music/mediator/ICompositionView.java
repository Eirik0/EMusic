package emu.music.mediator;

public interface ICompositionView {
    public int getX0();

    public int getY0();

    public int getWidth();

    public int getHeight();

    public void setSize(int width, int height);

    public void setPosition(int x, int y);

    public void repaint();
}
