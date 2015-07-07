package music.mediator;

import music.Duration;
import music.properties.NoteDimension;
import music.properties.TimeSignature;

public interface ISongProperties {
	public int getSelectedVoice();

	public NoteDimension getNoteDimension();

	public TimeSignature getTimeSignature();

	public void setTimeSignature(TimeSignature timeSignature);

	public int[] getInstruments();

	public void setInstruments(int[] instruments);

	public int getTempo();

	public void setTempo(int tempo);

	public Duration getPlayerStart();

	public boolean shouldDrawBars();

	public boolean shouldDrawKeys();
}
