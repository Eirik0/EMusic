package gui.properties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import music.mediator.SongMediator;
import music.properties.NoteDimension;

@SuppressWarnings("serial")
public class NoteDimensionPanel extends JPanel {
	private final SongMediator songMediator;

	private final NoteDimension noteDimension = new NoteDimension();

	public NoteDimensionPanel(SongMediator songMediator) {
		this.songMediator = songMediator;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JButton upButton = createPlusMinusButton("^", () -> noteDimension.setNoteHeight(noteDimension.getNoteHeight() + 0.5));
		JButton downButton = createPlusMinusButton("v", () -> noteDimension.setNoteHeight(noteDimension.getNoteHeight() - 0.5));
		JButton rightButton = createPlusMinusButton(">", () -> noteDimension.setSixteenthNoteWidth(noteDimension.getSixteenthNoteWidth() + 0.5));
		JButton leftButton = createPlusMinusButton("<", () -> noteDimension.setSixteenthNoteWidth(noteDimension.getSixteenthNoteWidth() - 0.5));

		JPanel upPanel = ComponentFactory.createPanel(null);
		upPanel.add(upButton, BorderLayout.CENTER);

		JPanel downPanel = ComponentFactory.createPanel(null);
		downPanel.add(downButton, BorderLayout.CENTER);

		add(new JLabel(" size "), BorderLayout.CENTER);
		add(upPanel, BorderLayout.NORTH);
		add(downPanel, BorderLayout.SOUTH);
		add(rightButton, BorderLayout.EAST);
		add(leftButton, BorderLayout.WEST);
	}

	private JButton createPlusMinusButton(String text, Runnable action) {
		JButton button = ComponentFactory.createButton(text, e -> {
			action.run();
			songMediator.resizeView();
		});
		button.setMargin(new Insets(0, 2, 0, 2));
		return button;
	}

	public NoteDimension getNoteDimension() {
		return noteDimension;
	}
}
