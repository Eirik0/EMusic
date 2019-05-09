package music.mediator;

import java.util.List;
import java.util.Map.Entry;

import music.Chord;
import music.Duration;

public interface ISongPlayer {
    public void playChord(Chord chord, ISongProperties songProperties);

    public void playChords(List<Entry<Duration, Chord>> chords, ISongProperties songProperties);
}
