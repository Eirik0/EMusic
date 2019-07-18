package emu.music.mediator;

import emu.music.Duration;
import emu.music.properties.DrawingOptions;
import emu.music.properties.NoteDimension;
import emu.music.properties.TimeSignature;

public interface ICompositionProperties {
    public int getSelectedVoice();

    public NoteDimension getNoteDimension();

    public TimeSignature getTimeSignature();

    public void setTimeSignature(TimeSignature timeSignature);

    public int[] getInstruments();

    public void setInstruments(int[] instruments);

    public int getTempo();

    public void setTempo(int tempo);

    public Duration getPlayerStart();

    public void setPlayerStart(Duration start);

    public DrawingOptions getDrawingOptions();
}
