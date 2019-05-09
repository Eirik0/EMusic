package gui.properties;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import main.EMusic;
import music.mediator.DrawerHelper;

@SuppressWarnings("serial")
public class SelectedVoicePanel extends JPanel {
    private int selectedVoice = 0;
    private final int[] instruments = new int[EMusic.NUMBER_OF_VOICES];

    final JComboBox<SelectableIsntrument> instrumentComboBox;

    public SelectedVoicePanel() {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setBackground(Color.WHITE);

        instrumentComboBox = ComponentCreator.createComboBox(SelectableIsntrument.INSTRUMENTS, SelectableIsntrument.INSTRUMENTS[0],
                instrument -> instruments[selectedVoice] = instrument.number);

        JToggleButton[] buttons = createVoiceSelectionButtons();

        JPanel buttonPanel = ComponentCreator.createPanel(new GridLayout(2, 8));
        for (JToggleButton button : buttons) {
            buttonPanel.add(button);
        }

        add(new JLabel("Track: "));
        add(Box.createHorizontalStrut(12));
        add(buttonPanel);
    }

    private JToggleButton[] createVoiceSelectionButtons() {
        JToggleButton[] buttons = new JToggleButton[EMusic.NUMBER_OF_VOICES];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < EMusic.NUMBER_OF_VOICES; ++i) {
            buttons[i] = createButton(i);
            buttonGroup.add(buttons[i]);
        }
        buttonGroup.setSelected(buttons[0].getModel(), true);
        return buttons;
    }

    private JToggleButton createButton(int i) {
        JToggleButton button = new JToggleButton();
        button.addActionListener(e -> {
            selectedVoice = i;
            instrumentComboBox.setSelectedIndex(instruments[selectedVoice]);
        });
        button.setFocusable(false);
        button.setMargin(new Insets(3, 3, 3, 3));
        button.setBackground(DrawerHelper.NOTE_COLORS[i]);

        BufferedImage selectedIconImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = selectedIconImage.createGraphics();
        graphics.setColor(DrawerHelper.NOTE_COLORS[i]);
        graphics.fillRect(0, 0, selectedIconImage.getWidth(), selectedIconImage.getHeight());
        button.setIcon(new ImageIcon(selectedIconImage));

        return button;
    }

    public int getSelectedVoice() {
        return selectedVoice;
    }

    public int[] getInstruments() {
        return instruments;
    }

    public void setInstruments(int[] instruments) {
        for (int i = 0; i < instruments.length; i++) {
            this.instruments[i] = instruments[i];
        }
        instrumentComboBox.setSelectedIndex(instruments[selectedVoice]);
    }
}
