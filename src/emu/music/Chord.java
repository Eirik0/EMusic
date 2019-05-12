package emu.music;

import emu.main.EMusic;

public class Chord implements Cloneable {
    private NoteContainer[] notes = new NoteContainer[EMusic.NUMBER_OF_VOICES];

    private Chord(NoteContainer[] notes) {
        this.notes = notes;
    }

    public Chord(Note note, int voice) {
        notes[voice] = new NoteContainer(note);
    }

    public void addNote(Note note, int voice) {
        if (notes[voice] == null) {
            notes[voice] = new NoteContainer(note);
        } else {
            notes[voice].addNote(note);
        }
    }

    public NoteContainer[] getNotes() {
        return notes;
    }

    public boolean isEmpty() {
        return numVoices() == 0;
    }

    public int numVoices() {
        int numVoices = 0;
        for (NoteContainer note : notes) {
            if (note != null) {
                ++numVoices;
            }
        }
        return numVoices;
    }

    public Duration totalDuration() {
        Duration maxDuration = Duration.ZERO;
        for (NoteContainer note : notes) {
            if (note != null) {
                Duration totalDuration = note.totalDuration();
                if (totalDuration.compareTo(maxDuration) > 0) {
                    maxDuration = totalDuration;
                }
            }
        }
        return maxDuration;
    }

    public Note getSingleNote(int voice) {
        if (voice >= notes.length) {
            return null;
        }
        return notes[voice] == null ? null : notes[voice].getNote();
    }

    @Override
    public Chord clone() {
        int length = notes.length;
        NoteContainer[] cloneNotes = new NoteContainer[length];
        for (int i = 0; i < length; i++) {
            NoteContainer noteContainer = notes[i];
            if (noteContainer != null) {
                cloneNotes[i] = noteContainer.clone();
            }
        }
        return new Chord(cloneNotes);
    }
}