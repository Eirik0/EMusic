package music.mediator.drawer;

import java.awt.Color;

public class MockString {
    public int x0;
    public int y0;
    public String text;
    public Color color;

    public MockString(int x0, int y0, String text, Color color) {
        this.x0 = x0;
        this.y0 = y0;
        this.text = text;
        this.color = color;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        MockString other = (MockString) obj;
        if (color == null) {
            if (other.color != null) {
                return false;
            }
        } else if (!color.equals(other.color)) {
            return false;
        }
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
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
        return "MockString [x0=" + x0 + ", y0=" + y0 + ", text=" + text + ", color=" + color + "]";
    }
}
