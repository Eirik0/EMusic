package emu.music;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Composition {
    private final TreeMap<Duration, Chord> chords;

    public Composition() {
        chords = new TreeMap<>();
    }

    public Chord addNote(Duration noteStart, Note note, int voice) {
        Chord chord = chords.get(noteStart);
        if (chord == null) {
            chord = new Chord(note, voice);
            chords.put(noteStart, chord);
        } else {
            chord.addNote(note, voice);
        }
        return chord;
    }

    public Chord getChord(Duration noteStart) {
        return chords.get(noteStart);
    }

    public Duration totalDuration() {
        Duration maxDuration = Duration.ZERO;
        for (Map.Entry<Duration, Chord> chordEntry : chords.entrySet()) {
            Duration noteStart = chordEntry.getKey();
            Duration chordDuration = chordEntry.getValue().totalDuration();
            Duration duration = noteStart.add(chordDuration);
            if (duration.compareTo(maxDuration) > 0) {
                maxDuration = duration;
            }
        }
        return maxDuration;
    }

    public Iterator<Entry<Duration, Chord>> getEntryIterator() {
        return chords.entrySet().iterator();
    }

    public ArrayList<Map.Entry<Duration, Chord>> chordList() {
        return new ArrayList<>(chords.entrySet());
    }
}