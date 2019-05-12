package emu.music.mediator;

import emu.music.mediator.ISongView;

public class TestView implements ISongView {
    public int x0;
    public int y0;
    public int width;
    public int height;

    public TestView(int x0, int y0, int width, int height) {
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getX0() {
        return x0;
    }

    @Override
    public int getY0() {
        return y0;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setPosition(int x, int y) {
        x0 = x;
        y0 = y;
    }

    @Override
    public void repaint() {
        // TODO Auto-generated method stub
    }
}
