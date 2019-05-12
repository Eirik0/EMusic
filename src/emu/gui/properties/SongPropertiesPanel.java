package emu.gui.properties;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import emu.music.mediator.SongMediator;
import emu.music.mediator.state.NoteEntryState;
import emu.music.mediator.state.SongPlayingState;
import emu.music.properties.DrawingOptions;
import emu.music.properties.NoteDimension;
import emu.music.properties.TimeSignature;

@SuppressWarnings("serial")
public class SongPropertiesPanel extends JPanel {
    private static final int DEFAULT_TEMPO = 120;

    private final SelectedVoicePanel selectedVoicePanel;
    private final TimeSignaturePanel timeSignaturePanel;
    private final NoteDimensionPanel noteDimensionPanel;
    private final DrawerOptionsPanel drawerOptionsPanel;

    private int tempo = DEFAULT_TEMPO;

    private final JSlider tempoSlider;
    private final JLabel tempoValueLabel = new JLabel(String.valueOf(DEFAULT_TEMPO));

    public SongPropertiesPanel(SongMediator songMediator) {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setBackground(Color.WHITE);

        selectedVoicePanel = new SelectedVoicePanel();
        timeSignaturePanel = new TimeSignaturePanel(songMediator);
        noteDimensionPanel = new NoteDimensionPanel(songMediator);
        drawerOptionsPanel = new DrawerOptionsPanel(songMediator);

        tempoSlider = ComponentCreator.createSlider(SwingConstants.HORIZONTAL, 10, 300, 30, DEFAULT_TEMPO, value -> {
            tempo = value;
            tempoValueLabel.setText(String.valueOf(tempo));
        });

        add(ComponentCreator.createSplitPane(JSplitPane.VERTICAL_SPLIT, createTopPanel(), createBottomPanel()));
        add(Box.createHorizontalStrut(20));
        add(ComponentCreator.createSplitPane(JSplitPane.VERTICAL_SPLIT,
                ComponentCreator.createButton("Play", e -> songMediator.setState(SongPlayingState.class)),
                ComponentCreator.createButton("Stop", e -> songMediator.setState(NoteEntryState.class))));
        add(Box.createHorizontalStrut(20));
        add(noteDimensionPanel);
        add(Box.createHorizontalStrut(20));
        add(new OpenSavePanel(songMediator));
        add(Box.createHorizontalStrut(20));
        add(drawerOptionsPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = ComponentCreator.createPanel(new FlowLayout(FlowLayout.LEADING));

        topPanel.add(new JLabel("Instrument: "));
        topPanel.add(selectedVoicePanel.instrumentComboBox);
        topPanel.add(Box.createHorizontalStrut(36));
        topPanel.add(new JLabel("Tempo: "));
        topPanel.add(tempoSlider);
        topPanel.add(tempoValueLabel);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = ComponentCreator.createPanel(new FlowLayout(FlowLayout.LEADING));

        bottomPanel.add(selectedVoicePanel);
        bottomPanel.add(Box.createHorizontalStrut(12));
        bottomPanel.add(timeSignaturePanel);

        return bottomPanel;
    }

    // get/set properties
    public int getSelectedVoice() {
        return selectedVoicePanel.getSelectedVoice();
    }

    public NoteDimension getNoteDimension() {
        return noteDimensionPanel.getNoteDimension();
    }

    public TimeSignature getTimeSignature() {
        return timeSignaturePanel.getTimeSignature();
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        timeSignaturePanel.setTimeSignature(timeSignature);
    }

    public int[] getInstruments() {
        return selectedVoicePanel.getInstruments();
    }

    public void setInstruments(int[] instruments) {
        selectedVoicePanel.setInstruments(instruments);
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
        tempoSlider.setValue(tempo);
    }

    public DrawingOptions getDrawingOptions() {
        return drawerOptionsPanel.getDrawingOptions();
    }
}
