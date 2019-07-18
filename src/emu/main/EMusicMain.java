package emu.main;

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

import emu.gui.PianoKeyRowHeader;
import emu.gui.SongScrollablePanel;
import emu.gui.TimeSignatureColumnHeader;
import emu.gui.mediator.GraphicsDrawer;
import emu.gui.mediator.ThreadTimer;
import emu.gui.mediator.ViewSongProperties;
import emu.gui.mediator.ViewUserInput;
import emu.gui.mediator.ViewportView;
import emu.gui.mouse.HeaderMouseController;
import emu.gui.mouse.SongMouseAdapter;
import emu.gui.mouse.ViewMouseController;
import emu.gui.properties.SongPropertiesPanel;
import emu.jmusic.JMusicPlayer;
import emu.music.mediator.SongMediator;
import emu.music.mediator.state.NoteEntryState;
import emu.music.properties.NoteDimension;
import emu.util.EMath;
import gt.component.ComponentCreator;

public class EMusicMain {
    private static final String TITLE = "EMusic";

    private static final int DEFAULT_COMPONENT_WIDTH = 1280;
    private static final int DEFAULT_COMPONENT_HEIGHT = 720;

    public static void main(String[] args) {
        ComponentCreator.setCrossPlatformLookAndFeel();

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
        JPanel cornerPanel = emu.gui.properties.ComponentCreator.createPanel(null);
        JButton backToStartButton = emu.gui.properties.ComponentCreator.createButton("<", e -> {
            songMediator.setPlayerStartFromHeader(true);
            timeSignatureColumnHeader.repaint();
        });
        backToStartButton.setMargin(new Insets(0, 2, 0, 2));
        cornerPanel.add(backToStartButton);
        scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, cornerPanel);
        scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, emu.gui.properties.ComponentCreator.createPanel(null));
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
