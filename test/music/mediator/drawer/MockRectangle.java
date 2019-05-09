package music.mediator.drawer;

import java.awt.Color;

public class MockRectangle {
    public int x0;
    public int y0;
    public int width;
    public int height;
    public Color color;
    public boolean isFilled;

    public MockRectangle(int x0, int y0, int width, int height, Color color, boolean isFilled) {
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isFilled = isFilled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + height;
        result = prime * result + (isFilled ? 1231 : 1237);
        result = prime * result + width;
        result = prime * result + x0;
        result = prime * result + y0;
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
        MockRectangle other = (MockRectangle) obj;
        if (color == null) {
            if (other.color != null) {
                return false;
            }
        } else if (!color.equals(other.color)) {
            return false;
        }
        if (height != other.height) {
            return false;
        }
        if (isFilled != other.isFilled) {
            return false;
        }
        if (width != other.width) {
            return false;
        }
        if (x0 != other.x0) {
            return false;
        }
        if (y0 != other.y0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MockRectangle [x0=" + x0 + ", y0=" + y0 + ", width=" + width + ", height=" + height + ", color=" + color + ", isFilled=" + isFilled + "]";
    }
}
