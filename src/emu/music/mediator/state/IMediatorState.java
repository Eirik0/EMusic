package emu.music.mediator.state;

import emu.music.Composition;

public interface IMediatorState {
    public void setComposition(Composition composition);

    public void mousePressed(int button);

    public void mouseReleased();

    public void draw();

    public void finish();
}
