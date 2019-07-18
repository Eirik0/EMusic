package emu.gui.mediator;

import emu.gui.TimeSignatureColumnHeader;
import emu.gui.properties.CompositionPropertiesPanel;
import emu.music.Duration;
import emu.music.mediator.ICompositionProperties;
import emu.music.properties.DrawingOptions;
import emu.music.properties.NoteDimension;
import emu.music.properties.TimeSignature;

public class ViewCompositionProperties implements ICompositionProperties {
    private final CompositionPropertiesPanel propertiesPanel;
    private final TimeSignatureColumnHeader timeSignatureHeader;

    public ViewCompositionProperties(CompositionPropertiesPanel propertiesPanel, TimeSignatureColumnHeader header) {
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
