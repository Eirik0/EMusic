package music.mediator.state;

import gui.mouse.SongMouseAdapter;

import java.util.Iterator;
import java.util.Map.Entry;

import music.Chord;
import music.Duration;
import music.Note;
import music.NoteContainer;
import music.Song;
import music.mediator.DrawerHelper;
import music.mediator.IDrawer;
import music.mediator.ISongPlayer;
import music.mediator.ISongProperties;
import music.mediator.ISongView;
import music.mediator.IUserInput;
import music.properties.NoteDimension;
import util.EMath;

public class NoteEntryState implements IMediatorState {
	private final ISongView view;
	private final IDrawer drawer;
	private final ISongPlayer player;

	private final ISongProperties songProperties;
	private final IUserInput userInput;

	private Song song;

	private int clickStartX;
	private int clickStartY;
	private boolean isAddingNote;

	public NoteEntryState(ISongView view, IDrawer drawer, ISongPlayer player, ISongProperties songProperties, IUserInput userInput) {
		this.view = view;
		this.drawer = drawer;
		this.player = player;
		this.songProperties = songProperties;
		this.userInput = userInput;
	}

	@Override
	public void setSong(Song song) {
		this.song = song;
	}

	@Override
	public void mousePressed(int button) {
		if (button == SongMouseAdapter.LEFT_CLICK) {
			clickStartX = view.getX0() + userInput.getMouseX();
			clickStartY = view.getY0() + userInput.getMouseY();
			isAddingNote = true;

			int key = (int) (clickStartY / songProperties.getNoteDimension().getNoteHeight());
			Note note = new Note(key, songProperties.getTimeSignature().getCalculatedDivision());

			Chord chord = song.getChord(durationStart(clickStartX));
			if (chord == null) {
				chord = new Chord(note, songProperties.getSelectedVoice());
			} else {
				chord = chord.clone();
				chord.addNote(note, songProperties.getSelectedVoice());
			}
			player.playChord(chord, songProperties);
		} else if (button == SongMouseAdapter.RIGHT_CLICK) {
			removeNote();
		}
	}

	@Override
	public void mouseReleased() {
		if (isAddingNote) {
			Duration noteStart = durationStart(clickStartX);
			Note note = createNote(noteStart, true);
			song.addNote(noteStart, note, songProperties.getSelectedVoice());
			isAddingNote = false;
		}
	}

	private Note createNote(Duration noteStart, boolean adding) {
		int clickEndX = view.getX0() + userInput.getMouseX();

		Duration noteEnd = durationStart(clickEndX);

		Duration minDuration = songProperties.getTimeSignature().getCalculatedDivision();
		Duration noteDuration = adding ? Duration.max(minDuration, noteEnd.subtract(noteStart).add(minDuration)) : minDuration;
		int noteY = adding ? clickStartY : view.getY0() + userInput.getMouseY();
		int key = (int) (noteY / songProperties.getNoteDimension().getNoteHeight());

		Note note = new Note(key, noteDuration);
		return note;
	}

	private Duration durationStart(int mouseX) {
		Duration minDuration = songProperties.getTimeSignature().getCalculatedDivision();
		double durationInPixels = songProperties.getNoteDimension().durationInPixels(minDuration);

		return new Duration((int) (mouseX / durationInPixels) * minDuration.beat, minDuration.division);
	}

	private void removeNote() {
		int mouseX = view.getX0() + userInput.getMouseX();
		int mouseY = view.getY0() + userInput.getMouseY();
		NoteDimension noteDimension = songProperties.getNoteDimension();
		double noteHeight = noteDimension.getNoteHeight();
		Iterator<Entry<Duration, Chord>> entryIterator = song.getEntryIterator();
		while (entryIterator.hasNext()) {
			Entry<Duration, Chord> durationChord = entryIterator.next();
			double chordStart = noteDimension.durationInPixels(durationChord.getKey());
			if (chordStart > mouseX) {
				break;
			}
			Chord chord = durationChord.getValue();
			NoteContainer[] notes = chord.getNotes();
			for (int i = 0; i < notes.length; ++i) {
				NoteContainer noteContainer = notes[i];
				if (noteContainer != null) {
					if (shouldRemove(noteContainer.getNote(), noteDimension, noteHeight, chordStart, mouseX, mouseY)) {
						noteContainer.removeNote();
					}
					Iterator<Note> noteIterator = noteContainer.getAdditionalNotes().iterator();
					while (noteIterator.hasNext()) {
						if (shouldRemove(noteIterator.next(), noteDimension, noteHeight, chordStart, mouseX, mouseY)) {
							noteIterator.remove();
						}
					}

					if (noteContainer.isEmpty()) {
						notes[i] = null;
					}
				}
			}
			if (chord.isEmpty()) {
				entryIterator.remove();
			}
		}
	}

	private boolean shouldRemove(Note note, NoteDimension noteDimension, double noteHeight, double chordStart, int mouseX, int mouseY) {
		int x0 = EMath.round(chordStart);
		int y0 = EMath.round(note.key * noteHeight);
		int x1 = EMath.round(chordStart + noteDimension.durationInPixels(note.duration));
		int y1 = EMath.round((note.key + 1) * noteHeight);
		return mouseX >= x0 && mouseY >= y0 && mouseX <= x1 && mouseY <= y1;
	}

	@Override
	public void draw() {
		if (isAddingNote) {
			Duration noteStartDuration = durationStart(clickStartX);
			Note note = createNote(noteStartDuration, true);
			double noteStartX = songProperties.getNoteDimension().durationInPixels(noteStartDuration);
			DrawerHelper.drawNote(drawer, view, songProperties.getNoteDimension(), note, noteStartX, songProperties.getSelectedVoice());
		} else if (userInput.isMouseEntered()) {
			Duration noteStartDuration = durationStart(view.getX0() + userInput.getMouseX());
			Note note = createNote(noteStartDuration, false);
			double noteStartX = songProperties.getNoteDimension().durationInPixels(noteStartDuration);
			DrawerHelper.drawNote(drawer, view, songProperties.getNoteDimension(), note, noteStartX, songProperties.getSelectedVoice());
		}
	}

	@Override
	public void finish() {
	}
}
