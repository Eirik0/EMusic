package music.mediator;

import java.util.List;
import java.util.Map.Entry;

import music.Chord;
import music.Duration;

public class NoOpPlayer implements ISongPlayer {
	@Override
	public void playChord(Chord chord, ISongProperties songProperties) {
	}

	@Override
	public void playChords(List<Entry<Duration, Chord>> chords, ISongProperties songProperties) {
	}
}
