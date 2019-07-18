package emu.music.mediator;

import java.util.List;
import java.util.Map.Entry;

import emu.music.Chord;
import emu.music.Duration;

public interface ICompositionPlayer {
    public void playChord(Chord chord, ICompositionProperties compositionProperties);

    public void playChords(List<Entry<Duration, Chord>> chords, ICompositionProperties compositionProperties);
}
