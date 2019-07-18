package emu.gui.properties;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import emu.music.mediator.CompositionMediator;

@SuppressWarnings("serial")
public class OpenSavePanel extends JPanel {
    private final JFileChooser fileChooser;

    public OpenSavePanel(CompositionMediator compositionMediator) {
        setBackground(Color.WHITE);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.mid", "mid"));

        JButton openButton = ComponentCreator.createButton("Open", e -> {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                compositionMediator.loadCompositionFromFile(selectedFile);
            }
        });

        JButton saveButton = ComponentCreator.createButton("Save", e -> {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                compositionMediator.saveCompositionToFile(selectedFile);
            }
        });

        add(ComponentCreator.createSplitPane(JSplitPane.VERTICAL_SPLIT, openButton, saveButton));
    }
}
