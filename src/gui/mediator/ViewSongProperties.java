package gui.mediator;

import gui.TimeSignatureColumnHeader;
import gui.properties.SongPropertiesPanel;
import music.Duration;
import music.mediator.ISongProperties;
import music.properties.DrawingOptions;
import music.properties.NoteDimension;
import music.properties.TimeSignature;

public class ViewSongProperties implements ISongProperties {
    private final SongPropertiesPanel propertiesPanel;
    private final TimeSignatureColumnHeader timeSignatureHeader;

    public ViewSongProperties(SongPropertiesPanel propertiesPanel, TimeSignatureColumnHeader header) {
        this.propertiesPanel = propertiesPanel;
        timeSignatureHeader = header;
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
        return timeSignatureHeader.getPlayerStart();
    }

    @Override
    public void setPlayerStart(Duration start) {
        timeSignatureHeader.setPlayerStart(start);
    }

    @Override
    public DrawingOptions getDrawingOptions() {
        return propertiesPanel.getDrawingOptions();
    }
}
