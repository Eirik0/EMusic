package emu.main;

import emu.music.Duration;

public class EMusic {
    public static final int HEADER_WIDTH = 48;
    public static final int HEADER_HEIGHT = 32;

    public static final Duration[] METERS = new Duration[] { new Duration(2, 4), new Duration(3, 2), new Duration(3, 4), new Duration(3, 8),
            new Duration(4, 4), new Duration(8, 4), new Duration(9, 8) };
    public static final Duration[] NOTE_DURATIONS = new Duration[] { new Duration(1, 1), new Duration(1, 2), new Duration(1, 4), new Duration(1, 8),
            new Duration(1, 16), new Duration(1, 32), new Duration(1, 64) };
    public static final Duration[] NOTE_DIVISIONS = new Duration[] { new Duration(1, 1), new Duration(1, 3), new Duration(1, 5), new Duration(1, 6) };

    public static final int NUMBER_OF_VOICES = 16;
}
