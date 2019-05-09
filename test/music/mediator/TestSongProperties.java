package music.mediator;

import main.EMusic;
import music.Duration;
import music.properties.DrawingOptions;
import music.properties.NoteDimension;
import music.properties.TimeSignature;

public class TestSongProperties implements ISongProperties {
	public int selectedVoice = 0;
	public NoteDimension noteDimension = new NoteDimension();
	private TimeSignature timeSignature = new TimeSignature(4, 4);
	private int[] instruments = new int[EMusic.NUMBER_OF_VOICES];
	private int tempo = 0;
	public Duration playerStart = Duration.ZERO;
	public DrawingOptions options = new DrawingOptions();

	@Override
	public int getSelectedVoice() {
		return selectedVoice;
	}

	@Override
	public NoteDimension getNoteDimension() {
		return noteDimension;
	}

	@Override
	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	@Override
	public int[] getInstruments() {
		return instruments;
	}

	@Override
	public int getTempo() {
		return tempo;
	}

	@Override
	public Duration getPlayerStart() {
		return playerStart;
	}

	@Override
	public void setPlayerStart(Duration start) {
		playerStart = start;
	}

	@Override
	public void setTimeSignature(TimeSignature timeSignature) {
		this.timeSignature = timeSignature;
	}

	@Override
	public void setInstruments(int[] instruments) {
		this.instruments = instruments;
	}

	@Override
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	@Override
	public DrawingOptions getDrawingOptions() {
		return options;
	}
}
