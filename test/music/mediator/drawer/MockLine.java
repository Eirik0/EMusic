package music.mediator.drawer;

import java.awt.Color;

public class MockLine {
	public int x0;
	public int y0;
	public int x1;
	public int y1;
	public Color color;

	public MockLine(int x0, int y0, int x1, int y1, Color color) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + x0;
		result = prime * result + x1;
		result = prime * result + y0;
		result = prime * result + y1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MockLine other = (MockLine) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (x0 != other.x0) {
			return false;
		}
		if (x1 != other.x1) {
			return false;
		}
		if (y0 != other.y0) {
			return false;
		}
		if (y1 != other.y1) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MockLine [x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 + ", y1=" + y1 + ", color=" + color + "]";
	}
}
