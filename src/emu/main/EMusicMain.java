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

import emu.gui.CompositionScrollablePanel;
import emu.gui.PianoKeyRowHeader;
import emu.gui.TimeSignatureColumnHeader;
import emu.gui.mediator.GraphicsDrawer;
import emu.gui.mediator.ThreadTimer;
import emu.gui.mediator.ViewCompositionProperties;
import emu.gui.mediator.ViewUserInput;
import emu.gui.mediator.ViewportView;
import emu.gui.mouse.CompositionMouseAdapter;
import emu.gui.mouse.HeaderMouseController;
import emu.gui.mouse.ViewMouseController;
import emu.gui.properties.CompositionPropertiesPanel;
import emu.jmusic.JMusicPlayer;
import emu.music.mediator.CompositionMediator;
import emu.music.mediator.state.NoteEntryState;
import emu.music.properties.NoteDimension;
import emu.util.EMath;

public class EMusicMain {
    private static final String TITLE = "EMusic";

    private static final int DEFAULT_COMPONENT_WIDTH = 1280;
    private static final int DEFAULT_COMPONENT_HEIGHT = 720;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame(TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setFocusable(false);

        JScrollPane scrollPane = createScrollPane();
        JViewport viewport = scrollPane.getViewport();

        CompositionMediator compositionMediator = new CompositionMediator(new GraphicsDrawer(), new JMusicPlayer(), new ThreadTimer());
        compositionMediator.setHeaderDrawers(new GraphicsDrawer(), new GraphicsDrawer());

        PianoKeyRowHeader pianoKeyRowHeader = new PianoKeyRowHeader(compositionMediator);

        TimeSignatureColumnHeader timeSignatureColumnHeader = new TimeSignatureColumnHeader(compositionMediator);
        CompositionMouseAdapter headerMouseAdapter = new CompositionMouseAdapter(new HeaderMouseController(compositionMediator, timeSignatureColumnHeader));
        timeSignatureColumnHeader.addMouseListener(headerMouseAdapter);
        timeSignatureColumnHeader.addMouseMotionListener(headerMouseAdapter);
        compositionMediator.setColumnHeaderUserInput(new ViewUserInput(headerMouseAdapter));

        setupScrollPane(scrollPane, compositionMediator, pianoKeyRowHeader, timeSignatureColumnHeader);

        ViewportView view = new ViewportView(viewport, pianoKeyRowHeader, timeSignatureColumnHeader);
        compositionMediator.setView(view);

        CompositionPropertiesPanel propertiesPanel = new CompositionPropertiesPanel(compositionMediator);
        ViewCompositionProperties compositionProperties = new ViewCompositionProperties(propertiesPanel, timeSignatureColumnHeader);
        compositionMediator.setCompositionProperties(compositionProperties);

        CompositionMouseAdapter compositionMouseAdapter = new CompositionMouseAdapter(new ViewMouseController(compositionMediator));
        compositionMediator.setUserInput(new ViewUserInput(compositionMouseAdapter));

        compositionMediator.setState(NoteEntryState.class);

        CompositionScrollablePanel compositionPanel = new CompositionScrollablePanel(compositionMediator);

        setupViewport(viewport, compositionMediator, compositionPanel, compositionMouseAdapter);

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

    private static void setupScrollPane(JScrollPane scrollPane, CompositionMediator compositionMediator, PianoKeyRowHeader pianoKeyRowHeader,
            TimeSignatureColumnHeader timeSignatureColumnHeader) {
        scrollPane.setRowHeaderView(pianoKeyRowHeader);
        scrollPane.setColumnHeaderView(timeSignatureColumnHeader);
        JPanel cornerPanel = emu.gui.properties.ComponentCreator.createPanel(null);
        JButton backToStartButton = emu.gui.properties.ComponentCreator.createButton("<", e -> {
            compositionMediator.setPlayerStartFromHeader(true);
            timeSignatureColumnHeader.repaint();
        });
        backToStartButton.setMargin(new Insets(0, 2, 0, 2));
        cornerPanel.add(backToStartButton);
        scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, cornerPanel);
        scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, emu.gui.properties.ComponentCreator.createPanel(null));
    }

    private static void setupViewport(JViewport viewport, CompositionMediator compositionMediator, CompositionScrollablePanel compositionPanel,
            CompositionMouseAdapter compositionMouseAdapter) {
        viewport.setBackground(Color.WHITE);
        viewport.setView(compositionPanel);
        viewport.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                compositionMediator.resizeView();
            }
        });
        viewport.addMouseListener(compositionMouseAdapter);
        viewport.addMouseMotionListener(compositionMouseAdapter);
        viewport.addMouseWheelListener(compositionMouseAdapter);
    }
}
