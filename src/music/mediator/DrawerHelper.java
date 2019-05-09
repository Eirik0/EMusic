package music.mediator;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map.Entry;

import main.EMusic;
import music.Chord;
import music.Duration;
import music.Note;
import music.NoteContainer;
import music.Song;
import music.properties.NoteDimension;
import util.EMath;

public class DrawerHelper {
	private static final Color CHARTREUSE = new Color(127, 255, 0);
	private static final Color DARK_CYAN = new Color(0, 175, 175);
	private static final Color DARK_GREEN = new Color(0, 100, 0);
	private static final Color DARK_MAGENTA = new Color(175, 0, 175);
	private static final Color DARK_ORANGE = new Color(255, 140, 0);
	private static final Color DARK_RED = new Color(178, 34, 34);
	private static final Color KHAKI = new Color(189, 183, 107);
	private static final Color SLATE_BLUE = new Color(106, 90, 205);
	private static final Color SPRING_GREEN = new Color(0, 255, 154);

	public static final Color SHARP_COLOR = new Color(212, 245, 245);
	public static final Color HEADER_SHARP_COLOR = Color.DARK_GRAY;
	public static final Color NATURAL_COLOR = Color.WHITE;

	public static final Color BAR_COLOR = Color.BLACK;
	public static final Color BEAT_COLOR = Color.LIGHT_GRAY;
	public static final Color NOTE_DIVISION_1_COLOR = new Color(140, 220, 190);
	public static final Color NOTE_DIVISION_2_COLOR = new Color(200, 200, 255);

	public static final Color PLAYER_BAR_COLOR = Color.RED;
	public static final Color TEXT_COLOR = Color.BLACK;

	public static final Color[] NOTE_COLORS = new Color[] {
		Color.RED, SLATE_BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.CYAN, CHARTREUSE, KHAKI,
		DARK_RED, Color.BLUE, DARK_GREEN, DARK_MAGENTA, DARK_ORANGE, DARK_CYAN, SPRING_GREEN, Color.YELLOW };

	public static void drawBackground(IDrawer drawer, ISongView view, ISongProperties songProperties) {
		drawer.setComponentSize(view.getWidth(), view.getHeight());
		if (songProperties.getDrawingOptions().shouldDrawKeys()) {
			drawKeys(drawer, view.getY0(), view.getWidth(), view.getHeight(), songProperties.getNoteDimension().getNoteHeight(), false);
		} else {
			drawer.setColor(Color.BLACK);
			drawer.fillRect(0, 0, view.getWidth(), view.getHeight());
		}
	}

	private static void drawKeys(IDrawer drawer, int y0, int width, int height, double noteHeight, boolean isHeader) {
		Color sharpColor = isHeader ? HEADER_SHARP_COLOR : SHARP_COLOR;
		double noteUpperY = -(y0 % noteHeight);
		int key = (int) (y0 / noteHeight);
		while (noteUpperY < height + noteHeight && key < NoteDimension.TOTAL_NOTES + 1) {
			int note = key % 12;
			// draw key
			if (note == 2 || note == 4 || note == 6 || note == 9 || note == 11) {
				drawer.setColor(sharpColor);
			} else {
				drawer.setColor(NATURAL_COLOR);
			}
			drawer.fillRect(0, EMath.round(noteUpperY), width, EMath.round(noteHeight));
			// draw line separating key
			drawer.setColor(sharpColor);
			drawer.drawLine(0, EMath.round(noteUpperY), width, EMath.round(noteUpperY));
			// mark "C"s in the header
			if (isHeader && note == 0) {
				drawer.setColor(TEXT_COLOR);
				drawer.drawCenteredString(8, EMath.round(noteUpperY + noteHeight / 2 - 3), "C" + (9 - key / 12));
			}
			noteUpperY += noteHeight;
			++key;
		}
	}

	public static void drawBars(IDrawer drawer, ISongView view, ISongProperties songProperties) {
		if (songProperties.getDrawingOptions().shouldDrawBars()) {
			drawBars(drawer, view, songProperties, false);
		}
	}

	private static void drawBars(IDrawer drawer, ISongView view, ISongProperties songProperties, boolean isHeader) {
		int barY0 = isHeader ? 16 : 0;
		int beatY0 = isHeader ? 24 : 0;
		int div1Y0 = isHeader ? 28 : 0;
		int div2Y0 = isHeader ? 30 : 0;
		int height = isHeader ? EMusic.HEADER_HEIGHT : view.getHeight();
		Color barColor = songProperties.getDrawingOptions().shouldDrawKeys() || isHeader ? BAR_COLOR : Color.WHITE;
		double divisionInPixels = songProperties.getNoteDimension().durationInPixels(songProperties.getTimeSignature().getCalculatedDivision());
		double noteDuration = songProperties.getNoteDimension().durationInPixels(songProperties.getTimeSignature().getNoteDuration());
		double beatInPixels = songProperties.getNoteDimension().durationInPixels(songProperties.getTimeSignature().getDurationOfBeat());
		double barInPixels = songProperties.getNoteDimension().durationInPixels(songProperties.getTimeSignature().getDurationOfBar());
		drawBars(drawer, view.getX0(), div2Y0, view.getWidth(), height, divisionInPixels, NOTE_DIVISION_2_COLOR);
		drawBars(drawer, view.getX0(), div1Y0, view.getWidth(), height, noteDuration, NOTE_DIVISION_1_COLOR);
		drawBars(drawer, view.getX0(), beatY0, view.getWidth(), height, beatInPixels, BEAT_COLOR);
		drawBars(drawer, view.getX0(), barY0, view.getWidth(), height, barInPixels, barColor);
	}

	private static void drawBars(IDrawer drawer, int x0, int y0, int width, int height, double barWidth, Color color) {
		double x = -(x0 % barWidth);
		drawer.setColor(color);
		while (x < width) {
			drawer.drawLine(EMath.round(x), y0, EMath.round(x), height);
			x += barWidth;
		}
	}

	public static void drawRowHeader(IDrawer drawer, ISongView view, ISongProperties songProperties) {
		drawer.setComponentSize(EMusic.HEADER_WIDTH, view.getHeight());
		drawKeys(drawer, view.getY0(), EMusic.HEADER_WIDTH, view.getHeight(), songProperties.getNoteDimension().getNoteHeight(), true);
	}

	public static void drawColumnHeader(IDrawer drawer, ISongView view, ISongProperties songProperties, IUserInput userInput) {
		drawer.setComponentSize(view.getWidth(), EMusic.HEADER_HEIGHT);
		drawer.setColor(Color.WHITE);
		drawer.fillRect(0, 0, view.getWidth(), EMusic.HEADER_HEIGHT);
		drawBars(drawer, view, songProperties, true);
		double barInPixels = songProperties.getNoteDimension().durationInPixels(songProperties.getTimeSignature().getDurationOfBar());
		drawBarText(drawer, view.getX0(), 6, view.getWidth(), barInPixels, TEXT_COLOR);
		drawColumnHeaderMouse(drawer, userInput);
		drawPlayerStart(drawer, view, songProperties);
	}

	private static void drawBarText(IDrawer drawer, int x0, int y0, int width, double barWidth, Color color) {
		double x = -(x0 % barWidth);
		int bar = (int) (x0 / barWidth) + 1;
		drawer.setColor(color);
		while (x < width) {
			drawer.drawCenteredString(EMath.round(x), y0, Integer.toString(bar));
			x += barWidth;
			++bar;
		}
	}

	private static void drawPlayerStart(IDrawer drawer, ISongView view, ISongProperties songProperties) {
		double x = songProperties.getNoteDimension().durationInPixels(songProperties.getPlayerStart()) - view.getX0();
		drawStartArrow(drawer, x, PLAYER_BAR_COLOR);
	}

	private static void drawStartArrow(IDrawer drawer, double x, Color color) {
		drawer.setColor(color);
		drawer.drawLine(EMath.round(x), EMath.round((double) EMusic.HEADER_HEIGHT / 2), EMath.round(x), EMusic.HEADER_HEIGHT);
		drawer.drawLine(EMath.round(x) - 3, EMusic.HEADER_HEIGHT - 5, EMath.round(x), EMusic.HEADER_HEIGHT);
		drawer.drawLine(EMath.round(x) + 3, EMusic.HEADER_HEIGHT - 5, EMath.round(x), EMusic.HEADER_HEIGHT);
	}

	public static void drawColumnHeaderMouse(IDrawer drawer, IUserInput userInput) {
		if (userInput.isMouseEntered()) {
			drawStartArrow(drawer, userInput.getMouseX(), BEAT_COLOR);
		}
	}

	public static void drawSong(IDrawer drawer, ISongView view, ISongProperties songProperties, Song song) {
		NoteDimension noteDimension = songProperties.getNoteDimension();
		int viewportEndX = view.getX0() + view.getWidth();
		Iterator<Entry<Duration, Chord>> entryIterator = song.getEntryIterator();
		while (entryIterator.hasNext()) {
			Entry<Duration, Chord> durationChord = entryIterator.next();
			double chordStart = noteDimension.durationInPixels(durationChord.getKey());
			if (chordStart - view.getX0() > viewportEndX) {
				break;
			}
			NoteContainer[] notes = durationChord.getValue().getNotes();
			for (int i = 0; i < notes.length; ++i) {
				NoteContainer noteContainer = notes[i];
				if (noteContainer != null) {
					drawNote(drawer, view, noteDimension, noteContainer.getNote(), chordStart, i, false);
					if (noteContainer.getAdditionalNotes().size() > 0) {
						for (Note note : noteContainer.getAdditionalNotes()) {
							drawNote(drawer, view, noteDimension, note, chordStart, i, false);
						}
					}
				}
			}
		}
	}

	public static void drawNote(IDrawer drawer, ISongView view, NoteDimension noteDimension, Note note, double chordStart, int voice, boolean isPlaying) {
		int offsetX = view.getX0();
		int offsetY = view.getY0();
		double noteHeight = noteDimension.getNoteHeight();
		int x0 = EMath.round(chordStart - offsetX) + 1;
		int y0 = EMath.round(note.key * noteHeight - offsetY) + 1;
		int x1 = Math.max(x0 + 1, EMath.round(chordStart + noteDimension.durationInPixels(note.duration) - offsetX));
		int y1 = Math.max(y0 + 1, EMath.round((note.key + 1) * noteHeight - offsetY));
		if (y1 >= 0 && x1 >= 0) {
			drawer.setColor(isPlaying ? inverse(NOTE_COLORS[voice]) : NOTE_COLORS[voice]);
			drawer.fillRect(x0, y0, x1 - x0, y1 - y0);
		}
	}

	public static Color inverse(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}
}
