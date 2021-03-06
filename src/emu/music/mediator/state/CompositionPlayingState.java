package emu.music.mediator.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.NoteContainer;
import emu.music.PositionedNote;
import emu.music.Composition;
import emu.music.mediator.DrawerHelper;
import emu.music.mediator.IDrawer;
import emu.music.mediator.ICompositionPlayer;
import emu.music.mediator.ICompositionProperties;
import emu.music.mediator.ICompositionView;
import emu.music.mediator.ITimer;
import emu.music.properties.NoteDimension;
import emu.util.EMath;

public class CompositionPlayingState implements IMediatorState {
    private final ICompositionView view;
    private final IDrawer drawer;
    private final ICompositionPlayer player;
    private final ITimer timer;
    private final ICompositionProperties compositionProperties;

    private CompositionPlayingRunnable compositionPlayingRunnable;
    private final Duration playerStart;

    private List<PositionedNote> notesBeingPlayed = Collections.emptyList();

    public CompositionPlayingState(ICompositionView view, IDrawer drawer, ICompositionPlayer player, ITimer timer, ICompositionProperties compositionProperties) {
        this.view = view;
        this.drawer = drawer;
        this.player = player;
        this.timer = timer;
        this.compositionProperties = compositionProperties;
        playerStart = compositionProperties.getPlayerStart();
    }

    @Override
    public void setComposition(Composition composition) {
        compositionPlayingRunnable = new CompositionPlayingRunnable(composition);
        new Thread(compositionPlayingRunnable).start();
    }

    @Override
    public void mousePressed(int button) {
    }

    @Override
    public void mouseReleased() {
    }

    @Override
    public void draw() {
        if (compositionProperties.getDrawingOptions().shouldDrawPlayingLine()) {
            int x = EMath.round(Math.min(view.getWidth() / 2, getPixelsElapsed() - view.getX0()));
            drawer.setColor(DrawerHelper.PLAYER_BAR_COLOR);
            drawer.drawLine(x, 0, x, view.getHeight());
        }

        NoteDimension noteDimension = compositionProperties.getNoteDimension();
        for (PositionedNote positionedNote : notesBeingPlayed) {
            double startInPixels = noteDimension.durationInPixels(positionedNote.chordStart);
            DrawerHelper.drawNote(drawer, view, noteDimension, positionedNote.note, startInPixels, positionedNote.voice, true);
        }
    }

    private double getPixelsElapsed() {
        double pixelsPerQuarterNote = compositionProperties.getNoteDimension().getSixteenthNoteWidth() * 4;
        double beatsElapsed = getBeatsElapsed();
        double offset = compositionProperties.getNoteDimension().durationInPixels(playerStart);
        return beatsElapsed * pixelsPerQuarterNote + offset;
    }

    private double getBeatsElapsed() {
        return timer.getTimeElapsed() * compositionProperties.getTempo() / 60000.0; // minutes to millis
    }

    @Override
    public void finish() {
        if (compositionPlayingRunnable != null) {
            compositionPlayingRunnable.stopRequested = true;
            // play an "empty" chord to stop the player playing
            player.playChord(new Chord(new Note(0, Duration.ZERO), 0), compositionProperties);
        }
    }

    private static List<PositionedNote> getNotesToPlay(Entry<Duration, Chord> chord) {
        List<PositionedNote> notes = new ArrayList<>();
        Duration chordStart = chord.getKey();
        NoteContainer[] noteContainers = chord.getValue().getNotes();
        for (int i = 0; i < noteContainers.length; i++) {
            NoteContainer noteContainer = noteContainers[i];
            if (noteContainer != null) {
                notes.add(new PositionedNote(noteContainer.getNote(), chordStart, i));
                for (Note note : noteContainer.getAdditionalNotes()) {
                    notes.add(new PositionedNote(note, chordStart, i));
                }
            }
        }
        return notes;
    }

    private class CompositionPlayingRunnable implements Runnable {
        private boolean stopRequested = false;
        private final Composition composition;

        public CompositionPlayingRunnable(Composition composition) {
            this.composition = composition;
        }

        @Override
        public void run() {
            Duration totalDuration = composition.totalDuration();
            ArrayList<Entry<Duration, Chord>> chordList = composition.chordList();
            int i = 0;
            for (; !stopRequested && i < chordList.size() && chordList.get(i).getKey().compareTo(playerStart) < 0; i++) {
                // skip notes before the requested start
            }
            if (i < chordList.size()) {
                List<Entry<Duration, Chord>> chords = new ArrayList<>();
                for (int j = i; !stopRequested && j < chordList.size(); ++j) {
                    chords.add(chordList.get(j));
                }
                player.playChords(chords, compositionProperties);
                timer.start();
            } else {
                return;
            }

            double pixelsElapsed = getPixelsElapsed();
            int lastX = Integer.MIN_VALUE;

            while (!stopRequested && pixelsElapsed < compositionProperties.getNoteDimension().durationInPixels(totalDuration)) {
                Duration currentDuration = playerStart.add(EMath.guessDuration(getBeatsElapsed() / 4));

                // find which notes are playing
                List<PositionedNote> newNotesToPlay = new ArrayList<>();
                for (PositionedNote playingNote : notesBeingPlayed) {
                    Duration noteEnd = playingNote.chordStart.add(playingNote.note.duration);
                    if (currentDuration.compareTo(playingNote.chordStart) >= 0 && currentDuration.compareTo(noteEnd) <= 0) {
                        newNotesToPlay.add(playingNote);
                    }
                }

                if (i < chordList.size()) {
                    Entry<Duration, Chord> nextChord = chordList.get(i);
                    if (nextChord.getKey().compareTo(currentDuration) < 0) {
                        newNotesToPlay.addAll(getNotesToPlay(nextChord));
                        ++i;
                    }
                }
                notesBeingPlayed = newNotesToPlay;

                // set position
                pixelsElapsed = getPixelsElapsed();
                double halfWidth = (double) view.getWidth() / 2;
                int x = pixelsElapsed <= view.getX0() ? EMath.round(pixelsElapsed) : EMath.round(pixelsElapsed - halfWidth);
                if (x != lastX) {
                    if (pixelsElapsed < view.getX0() || pixelsElapsed > view.getX0() + halfWidth) {
                        view.setPosition(Math.max(0, x), view.getY0());
                    } else {
                        view.repaint();
                    }
                }
                lastX = x;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
