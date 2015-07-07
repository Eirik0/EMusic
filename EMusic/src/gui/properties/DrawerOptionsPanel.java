package gui.properties;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import music.mediator.SongMediator;

@SuppressWarnings("serial")
public class DrawerOptionsPanel extends JPanel {
	private boolean drawKeys = true;
	private boolean drawBars = true;

	public DrawerOptionsPanel(SongMediator songMediator) {
		setBackground(Color.WHITE);

		JCheckBox keysChecBox = ComponentFactory.createCheckBox("Draw Keys", e -> {
			drawKeys = !drawKeys;
			songMediator.repaintView();
		});

		JCheckBox barsCheckBox = ComponentFactory.createCheckBox("Draw Bars", e -> {
			drawBars = !drawBars;
			songMediator.repaintView();
		});

		add(ComponentFactory.createSplitPane(JSplitPane.VERTICAL_SPLIT, keysChecBox, barsCheckBox));
	}

	public boolean shouldDrawBars() {
		return drawBars;
	}

	public boolean shouldDrawKeys() {
		return drawKeys;
	}
}
