package gui.properties;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import music.mediator.SongMediator;
import music.properties.DrawingOptions;

@SuppressWarnings("serial")
public class DrawerOptionsPanel extends JPanel {
	private final DrawingOptions options = new DrawingOptions();

	public DrawerOptionsPanel(SongMediator songMediator) {
		setBackground(Color.WHITE);
		JPanel optionsPanel = ComponentCreator.createPanel(new GridLayout(3, 1));

		optionsPanel.add(ComponentCreator.createCheckBox("Draw Keys", e -> {
			options.toggleDrawKeys();
			songMediator.repaintView();
		}));

		optionsPanel.add(ComponentCreator.createCheckBox("Draw Bars", e -> {
			options.toggleDrawBars();
			songMediator.repaintView();
		}));

		optionsPanel.add(ComponentCreator.createCheckBox("Draw Line When Playing", e -> {
			options.toggleDrawPlayingLine();
			songMediator.repaintView();
		}));

		add(optionsPanel);
	}

	public DrawingOptions getDrawingOptions() {
		return options;
	}
}
