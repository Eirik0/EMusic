package emu.gui.properties;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import emu.music.mediator.CompositionMediator;
import emu.music.properties.DrawingOptions;

@SuppressWarnings("serial")
public class DrawerOptionsPanel extends JPanel {
    private final DrawingOptions options = new DrawingOptions();

    public DrawerOptionsPanel(CompositionMediator compositionMediator) {
        setBackground(Color.WHITE);
        JPanel optionsPanel = ComponentCreator.createPanel(new GridLayout(3, 1));

        optionsPanel.add(ComponentCreator.createCheckBox("Draw Keys", e -> {
            options.toggleDrawKeys();
            compositionMediator.repaintView();
        }));

        optionsPanel.add(ComponentCreator.createCheckBox("Draw Bars", e -> {
            options.toggleDrawBars();
            compositionMediator.repaintView();
        }));

        optionsPanel.add(ComponentCreator.createCheckBox("Draw Line When Playing", e -> {
            options.toggleDrawPlayingLine();
            compositionMediator.repaintView();
        }));

        add(optionsPanel);
    }

    public DrawingOptions getDrawingOptions() {
        return options;
    }
}
