package emu.music.mediator.state;

import java.util.Iterator;
import java.util.Map.Entry;

import emu.gui.mouse.CompositionMouseAdapter;
import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.NoteContainer;
import emu.music.Composition;
import emu.music.mediator.DrawerHelper;
import emu.music.mediator.IDrawer;
import emu.music.mediator.ICompositionPlayer;
import emu.music.mediator.ICompositionProperties;
import emu.music.mediator.ICompositionView;
import emu.music.mediator.IUserInput;
import emu.music.properties.NoteDimension;
import emu.util.EMath;

public class NoteEntryState implements IMediatorState {
    private final ICompositionView view;
    private final IDrawer drawer;
    private final ICompositionPlayer player;

    private final ICompositionProperties compositionProperties;
    private final IUserInput userInput;

    private Composition composition;

    private int clickStartX;
    private int clickStartY;
    private boolean isAddingNote;

    public NoteEntryState(ICompositionView view, IDrawer drawer, ICompositionPlayer player, ICompositionProperties compositionProperties, IUserInput userInput) {
        this.view = view;
        this.drawer = drawer;
        this.player = player;
        this.compositionProperties = compositionProperties;
        this.userInput = userInput;
    }

    @Override
    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    @Override
    public void mousePressed(int button) {
        if (button == CompositionMouseAdapter.LEFT_CLICK) {
            clickStartX = view.getX0() + userInput.getMouseX();
            clickStartY = view.getY0() + userInput.getMouseY();
            isAddingNote = true;

            int key = (int) (clickStartY / compositionProperties.getNoteDimension().getNoteHeight());
            Note note = new Note(key, compositionProperties.getTimeSignature().getCalculatedDivision());

            Chord chord = composition.getChord(durationStart(clickStartX));
            if (chord == null) {
                chord = new Chord(note, compositionProperties.getSelectedVoice());
            } else {
                chord = chord.clone();
                chord.addNote(note, compositionProperties.getSelectedVoice());
            }
            player.playChord(chord, compositionProperties);
        } else if (button == CompositionMouseAdapter.RIGHT_CLICK) {
            removeNote();
        }
    }

    @Override
    public void mouseReleased() {
        if (isAddingNote) {
            Duration noteStart = durationStart(clickStartX);
            Note note = createNote(noteStart, true);
            composition.addNote(noteStart, note, compositionProperties.getSelectedVoice());
            isAddingNote = false;
        }
    }

    private Note createNote(Duration noteStart, boolean adding) {
        int clickEndX = view.getX0() + userInput.getMouseX();

        Duration noteEnd = durationStart(clickEndX);

        Duration minDuration = compositionProperties.getTimeSignature().getCalculatedDivision();
        Duration noteDuration = adding ? Duration.max(minDuration, noteEnd.subtract(noteStart).add(minDuration)) : minDuration;
        int noteY = adding ? clickStartY : view.getY0() + userInput.getMouseY();
        int key = (int) (noteY / compositionProperties.getNoteDimension().getNoteHeight());

        Note note = new Note(key, noteDuration);
        return note;
    }

    private Duration durationStart(int mouseX) {
        Duration minDuration = compositionProperties.getTimeSignature().getCalculatedDivision();
        double durationInPixels = compositionProperties.getNoteDimension().durationInPixels(minDuration);

        return new Duration((int) (mouseX / durationInPixels) * minDuration.beat, minDuration.division);
    }

    private void removeNote() {
        int mouseX = view.getX0() + userInput.getMouseX();
        int mouseY = view.getY0() + userInput.getMouseY();
        NoteDimension noteDimension = compositionProperties.getNoteDimension();
        double noteHeight = noteDimension.getNoteHeight();
        Iterator<Entry<Duration, Chord>> entryIterator = composition.getEntryIterator();
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

    private static boolean shouldRemove(Note note, NoteDimension noteDimension, double noteHeight, double chordStart, int mouseX, int mouseY) {
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
            double noteStartX = compositionProperties.getNoteDimension().durationInPixels(noteStartDuration);
            DrawerHelper.drawNote(drawer, view, compositionProperties.getNoteDimension(), note, noteStartX, compositionProperties.getSelectedVoice(), false);
        } else if (userInput.isMouseEntered()) {
            Duration noteStartDuration = durationStart(view.getX0() + userInput.getMouseX());
            Note note = createNote(noteStartDuration, false);
            double noteStartX = compositionProperties.getNoteDimension().durationInPixels(noteStartDuration);
            DrawerHelper.drawNote(drawer, view, compositionProperties.getNoteDimension(), note, noteStartX, compositionProperties.getSelectedVoice(), false);
        }
    }

    @Override
    public void finish() {
    }
}
