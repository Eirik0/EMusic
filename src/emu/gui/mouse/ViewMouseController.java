package emu.gui.mouse;

import emu.music.mediator.CompositionMediator;

public class ViewMouseController implements IMouseController {
    private final CompositionMediator compositionMediator;

    public ViewMouseController(CompositionMediator compositionMediator) {
        this.compositionMediator = compositionMediator;
    }

    @Override
    public void onMousePressed(int button) {
        compositionMediator.mousePressed(button);

    }

    @Override
    public void onMouseReleased() {
        compositionMediator.mouseReleased();
        compositionMediator.resizeView();
    }

    @Override
    public void onSetMouseXY() {
        compositionMediator.repaintView();
    }
}
