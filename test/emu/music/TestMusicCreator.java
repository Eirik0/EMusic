package emu.music;

import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.Composition;

public class TestMusicCreator {
    public static final Note QUARTER_NOTE = new Note(0, Duration.QUARTER_BEAT);

    public static Note quarterNote(int key) {
        return new Note(key, Duration.QUARTER_BEAT);
    }

    public static Note halfNote(int key) {
        return new Note(key, Duration.HALF_BEAT);
    }

    /**
     * new Chord(Duration.QUARTER_NOTE, 0)
     */
    public static Chord newChord() {
        return new Chord(QUARTER_NOTE, 0);
    }

    /**
     * C8, D8, E8, F8, G8, A8, B8, C9
     */
    public static Composition newWholeNoteScale() {
        return newWholeNoteScale(1);
    }

    public static Composition newWholeNoteScale(int voice) {
        Composition composition = new Composition();
        composition.addNote(Duration.ZERO, new Note(Note.C8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(1, 1), new Note(Note.D8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(2, 1), new Note(Note.E8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(3, 1), new Note(Note.F8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(4, 1), new Note(Note.G8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(5, 1), new Note(Note.A8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(6, 1), new Note(Note.B8, Duration.WHOLE_BEAT), voice);
        composition.addNote(new Duration(7, 1), new Note(Note.C9, Duration.WHOLE_BEAT), voice);
        return composition;
    }
}
