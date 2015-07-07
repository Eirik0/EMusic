package music.properties;

import music.Duration;
import music.Note;
import util.EMath;

public class NoteDimension {
	public static final int TOTAL_NOTES = Note.C0 + 1;

	public static final double DEFAULT_SIXTEENTH_WIDTH = 16;
	public static final double DEFAULT_SIXTEENTH_HEIGHT = 16;

	private double sixteenthNoteWidth;
	private double noteHeight;

	public NoteDimension() {
		sixteenthNoteWidth = DEFAULT_SIXTEENTH_WIDTH;
		noteHeight = DEFAULT_SIXTEENTH_HEIGHT;
	}

	public double getSixteenthNoteWidth() {
		return sixteenthNoteWidth;
	}

	public void setSixteenthNoteWidth(double sixteenthNoteWidth) {
		this.sixteenthNoteWidth = sixteenthNoteWidth;
	}

	public double getNoteHeight() {
		return noteHeight;
	}

	public void setNoteHeight(double sixteenthNoteHeight) {
		noteHeight = sixteenthNoteHeight;
	}

	public double durationInPixels(Duration duration) {
		return 16 * sixteenthNoteWidth * duration.doubleValue();
	}

	public int songHeightInPixels() {
		return EMath.round(noteHeight * TOTAL_NOTES);
	}
}
