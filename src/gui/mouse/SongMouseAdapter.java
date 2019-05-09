package gui.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SongMouseAdapter extends MouseAdapter {
    public static final int LEFT_CLICK = 1;
    public static final int RIGHT_CLICK = 3;

    private boolean isMouseEntered = false;
    private int mouseX;
    private int mouseY;

    private final IMouseController controller;

    public SongMouseAdapter(IMouseController controller) {
        this.controller = controller;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isMouseEntered() {
        return isMouseEntered;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.onMousePressed(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.onMouseReleased();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseEntered = true;
        setMouseXY(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseEntered = false;
        setMouseXY(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setMouseXY(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setMouseXY(e);
    }

    private void setMouseXY(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        controller.onSetMouseXY();
    }
}
