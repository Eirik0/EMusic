package emu.music;

public class Note {
    public static final int REST = -1;

    public static final int C9 = 0;
    public static final int B8 = 1;
    public static final int As8 = 2;
    public static final int A8 = 3;
    public static final int Gs8 = 4;
    public static final int G8 = 5;
    public static final int Fs8 = 6;
    public static final int F8 = 7;
    public static final int E8 = 8;
    public static final int Ds8 = 9;
    public static final int D8 = 10;
    public static final int Cs8 = 11;
    public static final int C8 = 12;
    // ...
    public static final int C4 = 60;
    // ...
    public static final int C0 = 108;

    public final int key;
    public final Duration duration;

    public Note(int key, Duration duration) {
        this.key = key;
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * (prime + key) + ((duration == null) ? 0 : duration.hashCode());
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
        Note other = (Note) obj;
        if (duration == null) {
            return other.duration == null && key == other.key;
        }
        return key == other.key && duration.equals(other.duration);
    }

    @Override
    public String toString() {
        return key + " " + duration;
    }
}
