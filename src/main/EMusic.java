package main;

import gui.PianoKeyRowHeader;
import gui.SongScrollablePanel;
import gui.TimeSignatureColumnHeader;
import gui.mediator.GraphicsDrawer;
import gui.mediator.ThreadTimer;
import gui.mediator.ViewSongProperties;
import gui.mediator.ViewUserInput;
import gui.mediator.ViewportView;
import gui.mouse.HeaderMouseController;
import gui.mouse.SongMouseAdapter;
import gui.mouse.ViewMouseController;
import gui.properties.ComponentCreator;
import gui.properties.SongPropertiesPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jmusic.JMusicPlayer;
import music.Duration;
import music.mediator.SongMediator;
import music.mediator.state.NoteEntryState;
import music.properties.NoteDimension;
import util.EMath;

public class EMusic {
	private static final String TITLE = "EMusic";

	private static final int DEFAULT_COMPONENT_WIDTH = 1280;
	private static final int DEFAULT_COMPONENT_HEIGHT = 720;

	public static final int HEADER_WIDTH = 48;
	public static final int HEADER_HEIGHT = 32;

	public static final Duration[] METERS = new Duration[] { new Duration(2, 4), new Duration(3, 2), new Duration(3, 4), new Duration(3, 8),
		new Duration(4, 4), new Duration(8, 4), new Duration(9, 8) };
	public static final Duration[] NOTE_DURATIONS = new Duration[] { new Duration(1, 1), new Duration(1, 2), new Duration(1, 4), new Duration(1, 8),
			new Duration(1, 16), new Duration(1, 32), new Duration(1, 64) };
	public static final Duration[] NOTE_DIVISIONS = new Duration[] { new Duration(1, 1), new Duration(1, 3), new Duration(1, 5), new Duration(1, 6) };

	public static final int NUMBER_OF_VOICES = 16;

	public static void main(String[] args) {
		try {
			// the windows look and feel has a much nicer JFileChooser than the default
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		}

		JFrame mainFrame = new JFrame(TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setFocusable(false);

		JScrollPane scrollPane = createScrollPane();
		JViewport viewport = scrollPane.getViewport();

		SongMediator songMediator = new SongMediator(new GraphicsDrawer(), new JMusicPlayer(), new ThreadTimer());
		songMediator.setHeaderDrawers(new GraphicsDrawer(), new GraphicsDrawer());

		PianoKeyRowHeader pianoKeyRowHeader = new PianoKeyRowHeader(songMediator);

		TimeSignatureColumnHeader timeSignatureColumnHeader = new TimeSignatureColumnHeader(songMediator);
		SongMouseAdapter headerMouseAdapter = new SongMouseAdapter(new HeaderMouseController(songMediator, timeSignatureColumnHeader));
		timeSignatureColumnHeader.addMouseListener(headerMouseAdapter);
		timeSignatureColumnHeader.addMouseMotionListener(headerMouseAdapter);
		songMediator.setColumnHeaderUserInput(new ViewUserInput(headerMouseAdapter));

		setupScrollPane(scrollPane, songMediator, pianoKeyRowHeader, timeSignatureColumnHeader);

		ViewportView view = new ViewportView(viewport, pianoKeyRowHeader, timeSignatureColumnHeader);
		songMediator.setView(view);

		SongPropertiesPanel propertiesPanel = new SongPropertiesPanel(songMediator);
		ViewSongProperties songProperties = new ViewSongProperties(propertiesPanel, timeSignatureColumnHeader);
		songMediator.setSongProperties(songProperties);

		SongMouseAdapter songMouseAdapter = new SongMouseAdapter(new ViewMouseController(songMediator));
		songMediator.setUserInput(new ViewUserInput(songMouseAdapter));

		songMediator.setState(NoteEntryState.class);

		SongScrollablePanel songPanel = new SongScrollablePanel(songMediator);

		setupViewport(viewport, songMediator, songPanel, songMouseAdapter);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(propertiesPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

		viewport.setViewPosition(new Point(0, EMath.round(40 * NoteDimension.DEFAULT_SIXTEENTH_HEIGHT)));
	}

	private static JScrollPane createScrollPane() {
		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(DEFAULT_COMPONENT_WIDTH, DEFAULT_COMPONENT_HEIGHT));
		scrollPane.setWheelScrollingEnabled(false);
		return scrollPane;
	}

	private static void setupScrollPane(JScrollPane scrollPane, SongMediator songMediator, PianoKeyRowHeader pianoKeyRowHeader,
			TimeSignatureColumnHeader timeSignatureColumnHeader) {
		scrollPane.setRowHeaderView(pianoKeyRowHeader);
		scrollPane.setColumnHeaderView(timeSignatureColumnHeader);
		JPanel cornerPanel = ComponentCreator.createPanel(null);
		JButton backToStartButton = ComponentCreator.createButton("<", e -> {
			songMediator.setPlayerStartFromHeader(true);
			timeSignatureColumnHeader.repaint();
		});
		backToStartButton.setMargin(new Insets(0, 2, 0, 2));
		cornerPanel.add(backToStartButton);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, cornerPanel);
		scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, ComponentCreator.createPanel(null));
	}

	private static void setupViewport(JViewport viewport, SongMediator songMediator, SongScrollablePanel songPanel, SongMouseAdapter songMouseAdapter) {
		viewport.setBackground(Color.WHITE);
		viewport.setView(songPanel);
		viewport.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				songMediator.resizeView();
			}
		});
		viewport.addMouseListener(songMouseAdapter);
		viewport.addMouseMotionListener(songMouseAdapter);
		viewport.addMouseWheelListener(songMouseAdapter);
	}
}
