package emu.gui.mediator;

import emu.gui.mouse.CompositionMouseAdapter;
import emu.music.mediator.IUserInput;

public class ViewUserInput implements IUserInput {
    private final CompositionMouseAdapter sma;

    public ViewUserInput(CompositionMouseAdapter sma) {
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
