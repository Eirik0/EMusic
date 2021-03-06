package emu.gui.properties;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import emu.main.EMusic;
import emu.music.Duration;
import emu.music.mediator.CompositionMediator;
import emu.music.properties.TimeSignature;

@SuppressWarnings("serial")
public class TimeSignaturePanel extends JPanel {
    private final TimeSignature timeSignature = new TimeSignature(4, 4);

    private final JComboBox<Duration> meterComboBox;
    private final JComboBox<Duration> noteDuratioComboBox;
    private final JComboBox<Duration> divisionComboBox;

    public TimeSignaturePanel(CompositionMediator compositionMediator) {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setBackground(Color.WHITE);

        meterComboBox = ComponentCreator.createComboBox(EMusic.METERS, timeSignature.getMeter(),
                duration -> {
                    timeSignature.setMeter(duration);
                    compositionMediator.repaintView();
                });
        noteDuratioComboBox = ComponentCreator.createComboBox(EMusic.NOTE_DURATIONS, timeSignature.getNoteDuration(),
                duration -> {
                    timeSignature.setNoteDuration(duration);
                    compositionMediator.repaintView();
                });
        divisionComboBox = ComponentCreator.createComboBox(EMusic.NOTE_DIVISIONS, timeSignature.getCalculatedDivision(),
                duration -> {
                    timeSignature.setDivision(duration);
                    compositionMediator.repaintView();
                });

        add(new JLabel("Meter: "));
        add(meterComboBox);
        add(new JLabel("Note: "));
        add(noteDuratioComboBox);
        add(new JLabel("Division: "));
        add(divisionComboBox);
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        boolean found = false;
        for (int i = 0; i < EMusic.METERS.length; i++) {
            if (EMusic.METERS[i].equals(timeSignature.getMeter())) {
                found = true;
            }
        }
        if (!found) {
            throw new IllegalStateException("Unimplemented time signature " + timeSignature.getMeter());
        }
        meterComboBox.setSelectedItem(timeSignature.getMeter());
        noteDuratioComboBox.setSelectedItem(timeSignature.getNoteDuration());
        divisionComboBox.setSelectedItem(timeSignature.getDivision());
    }
}
