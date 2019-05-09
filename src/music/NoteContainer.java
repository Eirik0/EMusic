package music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteContainer implements Cloneable {
    private Note note;
    private List<Note> additionalNotes = Collections.emptyList();

    public NoteContainer(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void removeNote() {
        if (additionalNotes.isEmpty()) {
            note = null;
        } else {
            note = additionalNotes.get(0);
            additionalNotes.remove(0);
        }
    }

    public void addNote(Note note) {
        if (additionalNotes.isEmpty()) {
            additionalNotes = new ArrayList<Note>();
        }
        additionalNotes.add(note);
    }

    public List<Note> getAdditionalNotes() {
        return additionalNotes;
    }

    public boolean isEmpty() {
        return note == null && additionalNotes.isEmpty();
    }

    public Duration totalDuration() {
        Duration maxDuration = note.duration;
        for (Note note : additionalNotes) {
            if (note.duration.compareTo(maxDuration) > 0) {
                maxDuration = note.duration;
            }
        }
        return maxDuration;
    }

    @Override
    protected NoteContainer clone() {
        NoteContainer clone = new NoteContainer(note);
        for (Note note : additionalNotes) {
            clone.addNote(note);
        }
        return clone;
    }
}
