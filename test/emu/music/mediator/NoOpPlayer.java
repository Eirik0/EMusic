package emu.music.mediator;

import java.util.List;
import java.util.Map.Entry;

import emu.music.Chord;
import emu.music.Duration;
import emu.music.mediator.ICompositionPlayer;
import emu.music.mediator.ICompositionProperties;

public class NoOpPlayer implements ICompositionPlayer {
    @Override
    public void playChord(Chord chord, ICompositionProperties compositionProperties) {
    }

    @Override
    public void playChords(List<Entry<Duration, Chord>> chords, ICompositionProperties compositionProperties) {
    }
}
