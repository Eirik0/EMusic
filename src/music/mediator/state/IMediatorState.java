package music.mediator.state;

import music.Song;

public interface IMediatorState {
    public void setSong(Song song);

    public void mousePressed(int button);

    public void mouseReleased();

    public void draw();

    public void finish();
}
