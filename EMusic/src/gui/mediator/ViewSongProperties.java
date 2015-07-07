package gui.mediator;

import gui.TimeSignatureColumnHeader;
import gui.properties.SongPropertiesPanel;
import music.Duration;
import music.mediator.ISongProperties;
import music.properties.NoteDimension;
import music.properties.TimeSignature;

public class ViewSongProperties implements ISongProperties {
	private final SongPropertiesPanel propertiesPanel;
	private final TimeSignatureColumnHeader header;

	public ViewSongProperties(SongPropertiesPanel propertiesPanel, TimeSignatureColumnHeader header) {
		this.propertiesPanel = propertiesPanel;
		this.header = header;
	}

	@Override
	public int getSelectedVoice() {
		return propertiesPanel.getSelectedVoice();
	}

	@Override
	public NoteDimension getNoteDimension() {
		return propertiesPanel.getNoteDimension();
	}

	@Override
	public TimeSignature getTimeSignature() {
		return propertiesPanel.getTimeSignature();
	}

	@Override
	public void setTimeSignature(TimeSignature timeSignature) {
		propertiesPanel.setTimeSignature(timeSignature);
	}

	@Override
	public int[] getInstruments() {
		return propertiesPanel.getInstruments();
	}

	@Override
	public void setInstruments(int[] instruments) {
		propertiesPanel.setInstruments(instruments);
	}

	@Override
	public int getTempo() {
		return propertiesPanel.getTempo();
	}

	@Override
	public void setTempo(int tempo) {
		propertiesPanel.setTempo(tempo);
	}

	@Override
	public Duration getPlayerStart() {
		return header.getPlayerStart();
	}

	@Override
	public boolean shouldDrawBars() {
		return propertiesPanel.shouldDrawBars();
	}

	@Override
	public boolean shouldDrawKeys() {
		return propertiesPanel.shouldDrawKeys();
	}
}
