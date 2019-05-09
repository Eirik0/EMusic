package music.mediator;

public interface ITimer {
    public void start();

    public long getTimeElapsed();

    public void stop();
}
