package emu.gui.mediator;

import emu.gui.mouse.SongMouseAdapter;
import emu.music.mediator.IUserInput;

public class ViewUserInput implements IUserInput {
    private final SongMouseAdapter sma;

    public ViewUserInput(SongMouseAdapter sma) {
        this.sma = sma;
    }

    @Override
    public int getMouseX() {
        return sma.getMouseX();
    }

    @Override
    public int getMouseY() {
        return sma.getMouseY();
    }

    @Override
    public boolean isMouseEntered() {
        return sma.isMouseEntered();
    }
}
