package emu.music.mediator;

import java.util.List;
import java.util.Map.Entry;

import emu.music.Chord;
import emu.music.Duration;
import emu.music.mediator.ISongPlayer;
import emu.music.mediator.ISongProperties;

public class NoOpPlayer implements ISongPlayer {
    @Override
    public void playChord(Chord chord, ISongProperties songProperties) {
    }

    @Override
    public void playChords(List<Entry<Duration, Chord>> chords, ISongProperties songProperties) {
    }
}
