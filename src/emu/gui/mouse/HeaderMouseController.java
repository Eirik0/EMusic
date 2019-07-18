package emu.gui.mouse;

import emu.gui.TimeSignatureColumnHeader;
import emu.music.mediator.CompositionMediator;

public class HeaderMouseController implements IMouseController {
    private final CompositionMediator compositionMediator;
    private final TimeSignatureColumnHeader header;

    public HeaderMouseController(CompositionMediator compositionMediator, TimeSignatureColumnHeader timeSignatureColumnHeader) {
        this.compositionMediator = compositionMediator;
        header = timeSignatureColumnHeader;
    }

    @Override
    public void onMousePressed(int button) {
        compositionMediator.setPlayerStartFromHeader(false);
    }

    @Override
    public void onMouseReleased() {
        header.repaint();
    }

    @Override
    public void onSetMouseXY() {
        header.repaint();
    }
}
