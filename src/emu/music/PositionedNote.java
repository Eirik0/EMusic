package emu.music;

import java.util.Objects;

public class PositionedNote {
    public final Note note;
    public final Duration chordStart;
    public final int voice;

    public PositionedNote(Note note, Duration chordStart, int voice) {
        this.note = note;
        this.chordStart = chordStart;
        this.voice = voice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, chordStart, Integer.valueOf(voice));
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
        PositionedNote other = (PositionedNote) obj;
        return Objects.equals(note, other.note) && Objects.equals(chordStart, other.chordStart) && voice == other.voice;
    }
}
