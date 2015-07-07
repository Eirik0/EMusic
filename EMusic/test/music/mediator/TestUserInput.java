package music.mediator;


public class TestUserInput implements IUserInput {
	public boolean isMouseEntered = true;
	private int mouseX;
	private int mouseY;

	public TestUserInput(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public void setMouseXY(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	public boolean isMouseEntered() {
		return isMouseEntered;
	}

	@Override
	public int getMouseX() {
		return mouseX;
	}

	@Override
	public int getMouseY() {
		return mouseY;
	}
}
