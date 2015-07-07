package gui.properties;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import music.mediator.SongMediator;

@SuppressWarnings("serial")
public class OpenSavePanel extends JPanel {
	private final JFileChooser fileChooser;

	public OpenSavePanel(SongMediator songMediator) {
		setBackground(Color.WHITE);

		fileChooser = new JFileChooser();

		JButton openButton = ComponentFactory.createButton("Open", e -> {
			if (fileChooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				songMediator.loadSongFromFile(selectedFile);
			}
		});

		JButton saveButton = ComponentFactory.createButton("Save", e -> {
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				songMediator.saveSongToFile(selectedFile);
			}
		});

		add(ComponentFactory.createSplitPane(JSplitPane.VERTICAL_SPLIT, openButton, saveButton));
	}
}
