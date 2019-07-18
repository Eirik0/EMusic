package emu.music.mediator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import emu.gui.mouse.CompositionMouseAdapter;
import emu.music.Chord;
import emu.music.Duration;
import emu.music.Note;
import emu.music.Composition;
import emu.music.TestMusicCreator;
import emu.music.mediator.drawer.MockLine;
import emu.music.mediator.drawer.MockRectangle;
import emu.music.mediator.drawer.TestDrawer;
import emu.music.properties.NoteDimension;

public class CompositionMediatorTest {
    // Scrollable size
    @Test
    public void testGetScrollableWidth_MinimumWidth() {
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 100, 100);
        int scrollableWidth = compositionMediator.getScrollableWidth();
        assertEquals(2 * 100, scrollableWidth);
    }

    @Test
    public void testGetScrollableWidth_BeyondMinimum() {
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 0, 0);
        int scrollableWidth = compositionMediator.getScrollableWidth();
        double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16; // = 2048 (8 whole notes)
        assertEquals(expectedWidth, scrollableWidth, 0.000001);
    }

    @Test
    public void testGetScrollableWidth_ExtraScreenSmall() {
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 100, 0);
        int scrollableWidth = compositionMediator.getScrollableWidth();
        double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16 + 100;
        assertEquals(expectedWidth, scrollableWidth, 0.000001);
    }

    @Test
    public void testGetScrollableWidth_ExtraScreenLarge() {
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 2000, 0);
        int scrollableWidth = compositionMediator.getScrollableWidth();
        double expectedWidth = 8 * NoteDimension.DEFAULT_SIXTEENTH_WIDTH * 16 + 2000; // 8 whole notes
        assertEquals(expectedWidth, scrollableWidth, 0.000001);
    }

    @Test
    public void testGetScrollableHeight() {
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0);
        int scrollableHeight = compositionMediator.getScrollableHeight();
        assertEquals(NoteDimension.DEFAULT_SIXTEENTH_HEIGHT * NoteDimension.TOTAL_NOTES, scrollableHeight, 0.000001);
    }

    // Draw background
    @Test
    public void testDrawKeys() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 160, 224, drawer);
        compositionMediator.drawBackground();
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 0, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "C");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 16, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "B");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 32, 159, 15, DrawerHelper.SHARP_COLOR, true)), "A#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 48, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "A");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 64, 159, 15, DrawerHelper.SHARP_COLOR, true)), "G#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 80, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "G");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 96, 159, 15, DrawerHelper.SHARP_COLOR, true)), "F#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 112, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "F");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 128, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "E");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 144, 159, 15, DrawerHelper.SHARP_COLOR, true)), "D#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 160, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "D");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 176, 159, 15, DrawerHelper.SHARP_COLOR, true)), "C#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 192, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "C2");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 208, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "B2");
    }

    @Test
    public void testDrawKeys_ScrollDownSlightly() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 8, 160, 224, drawer);
        compositionMediator.drawBackground();
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, -8, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "C");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 8, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "B");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 24, 159, 15, DrawerHelper.SHARP_COLOR, true)), "A#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 40, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "A");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 56, 159, 15, DrawerHelper.SHARP_COLOR, true)), "G#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 72, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "G");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 88, 159, 15, DrawerHelper.SHARP_COLOR, true)), "F#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 104, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "F");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 120, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "E");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 136, 159, 15, DrawerHelper.SHARP_COLOR, true)), "D#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 152, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "D");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 168, 159, 15, DrawerHelper.SHARP_COLOR, true)), "C#");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 184, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "C2");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 200, 159, 15, DrawerHelper.NATURAL_COLOR, true)), "B2");
        assertTrue(drawer.rectangles.contains(new MockRectangle(0, 216, 159, 15, DrawerHelper.SHARP_COLOR, true)), "A#2");
    }

    // Draw bars
    @Test
    public void testDrawBars() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 1000, 224, drawer);
        compositionMediator.drawBars();
        assertTrue(drawer.lines.contains(new MockLine(0, 0, 0, 224, DrawerHelper.BAR_COLOR)), "Bar 1");
        assertTrue(drawer.lines.contains(new MockLine(256, 0, 256, 224, DrawerHelper.BAR_COLOR)), "Bar 2");
        assertTrue(drawer.lines.contains(new MockLine(512, 0, 512, 224, DrawerHelper.BAR_COLOR)), "Bar 3");
        assertTrue(drawer.lines.contains(new MockLine(768, 0, 768, 224, DrawerHelper.BAR_COLOR)), "Bar 4");
    }

    @Test
    public void testDrawBars_Offset() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 25, 8, 1000, 224, drawer);
        compositionMediator.drawBars();
        assertTrue(drawer.lines.contains(new MockLine(231, 0, 231, 224, DrawerHelper.BAR_COLOR)), "Bar 2");
        assertTrue(drawer.lines.contains(new MockLine(487, 0, 487, 224, DrawerHelper.BAR_COLOR)), "Bar 3");
        assertTrue(drawer.lines.contains(new MockLine(743, 0, 743, 224, DrawerHelper.BAR_COLOR)), "Bar 4");
        assertTrue(drawer.lines.contains(new MockLine(999, 0, 999, 224, DrawerHelper.BAR_COLOR)), "Bar 5");
    }

    @Test
    public void testDrawNoteDivisions() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 250, 224, drawer);
        compositionMediator.drawBars();
        assertTrue(drawer.lines.contains(new MockLine(0, 0, 0, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)), "Division 1");
        assertTrue(drawer.lines.contains(new MockLine(64, 0, 64, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)), "Division 2");
        assertTrue(drawer.lines.contains(new MockLine(128, 0, 128, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)), "Division 3");
        assertTrue(drawer.lines.contains(new MockLine(192, 0, 192, 224, DrawerHelper.NOTE_DIVISION_1_COLOR)), "Division 4");
    }

    // Draw composition
    @Test
    public void testDrawComposition() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 0, 0, 2000, 224, drawer);
        compositionMediator.drawComposition();

        assertTrue(drawer.rectangles.contains(new MockRectangle(1, 193, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "C8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(257, 161, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "D8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(513, 129, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "E8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(769, 113, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "F8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(1025, 81, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "G8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(1281, 49, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "A8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(1537, 17, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "B8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(1793, 1, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "C9");
    }

    @Test
    public void testDrawComposition_Offset() {
        TestDrawer drawer = new TestDrawer();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(TestMusicCreator.newWholeNoteScale(), 100, 100, 2000, 224, drawer);
        compositionMediator.drawComposition();

        assertTrue(drawer.rectangles.contains(new MockRectangle(-99, 93, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "C8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(157, 61, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "D8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(413, 29, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "E8");
        assertTrue(drawer.rectangles.contains(new MockRectangle(669, 13, 254, 14, DrawerHelper.NOTE_COLORS[1], true)), "F8");
    }

    // Draw state
    @Test
    public void testDrawAddingSecondNoteWhileDragging() {
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(10, 0);
        Composition composition = new Composition();
        TestCompositionProperties testCompositionProperties = new TestCompositionProperties();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, testCompositionProperties, drawer, testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        testCompositionProperties.selectedVoice = 7;
        testInput.setMouseXY(10, 20);
        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        testInput.setMouseXY(70, 20);
        compositionMediator.drawState();

        assertNull(composition.chordList().get(0).getValue().getSingleNote(7));
        assertTrue(drawer.rectangles.contains(new MockRectangle(1, 17, 126, 14, DrawerHelper.NOTE_COLORS[7], true)));
    }

    @Test
    public void testDrawMouse() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(20, 20);
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 160, 224, testProperties, drawer, testInput);

        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(17, 17, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
    }

    @Test
    public void testDrawMouse_Offset() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(20, 20);
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 8, 8, 160, 224, testProperties, drawer, testInput);

        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(9, 9, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));

        drawer.clear();
        testInput.setMouseXY(25, 25);
        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(25, 25, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
    }

    @Test
    public void testDrawMouse_VerticalShift() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getTimeSignature().setNoteDuration(new Duration(1, 16));
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(0, 67);
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 160, 224, testProperties, drawer, testInput);

        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(1, 65, 14, 14, DrawerHelper.NOTE_COLORS[0], true)));
    }

    @Test
    public void testDrawMouse_SmallDivisions() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getTimeSignature().setNoteDuration(new Duration(1, 8));
        testProperties.getTimeSignature().setDivision(new Duration(1, 3));
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(112, 0);
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 300, 224, testProperties, drawer, testInput);

        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(108, 1, 8, 14, DrawerHelper.NOTE_COLORS[0], true)));

        drawer.clear();
        testInput.setMouseXY(122, 0);
        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(118, 1, 9, 14, DrawerHelper.NOTE_COLORS[0], true)));

        drawer.clear();
        testInput.setMouseXY(133, 0);
        compositionMediator.drawState();
        assertTrue(drawer.rectangles.contains(new MockRectangle(129, 1, 9, 14, DrawerHelper.NOTE_COLORS[0], true)));
    }

    @Test
    public void testDrawMouse_MouseExited() {
        TestDrawer drawer = new TestDrawer();
        TestUserInput testInput = new TestUserInput(0, 0);
        testInput.isMouseEntered = false;
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(new Composition(), 0, 0, 160, 224, drawer, testInput);

        compositionMediator.drawState();
        assertTrue(drawer.rectangles.isEmpty());
    }

    // Add note
    @Test
    public void testAddNote() {
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(Duration.ZERO, chordDuration.getKey());
        assertEquals(new Note(1, new Duration(1, 4)), chordDuration.getValue().getSingleNote(0));
    }

    @Test
    public void testAddNote_Offset() {
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 200, 200, 160, 224, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(new Duration(3, 4), chordDuration.getKey());
        assertEquals(new Note(13, new Duration(1, 4)), chordDuration.getValue().getSingleNote(0));
    }

    @Test
    public void testAddNote_Triplet_Offset() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.getTimeSignature().setNoteDuration(new Duration(1, 8));
        testProperties.getTimeSignature().setDivision(new Duration(1, 3));
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 200, 200, 160, 224, testProperties, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(new Duration(19, 24), chordDuration.getKey());
        assertEquals(new Note(13, new Duration(1, 24)), chordDuration.getValue().getSingleNote(0));
    }

    @Test
    public void testAddNote_Voice2() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        testProperties.selectedVoice = 2;
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, testProperties, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(Duration.ZERO, chordDuration.getKey());
        assertEquals(new Note(1, new Duration(1, 4)), chordDuration.getValue().getSingleNote(2));
    }

    @Test
    public void testAddNoteWithDrag() {
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, new TestDrawer(), testInput);
        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        testInput.setMouseXY(70, 20);
        compositionMediator.mouseReleased();

        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(Duration.ZERO, chordDuration.getKey());
        assertEquals(new Note(1, new Duration(1, 2)), chordDuration.getValue().getSingleNote(0));
        assertEquals(0, chordDuration.getValue().getNotes()[0].getAdditionalNotes().size());
    }

    @Test
    public void testAddTwoNotesWithDrag() {
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        testInput.setMouseXY(70, 20);
        compositionMediator.mouseReleased();

        testInput.setMouseXY(10, 40);
        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        testInput.setMouseXY(70, 40);
        compositionMediator.mouseReleased();

        assertEquals(1, composition.chordList().size());
        Entry<Duration, Chord> chordDuration = composition.chordList().get(0);
        assertEquals(Duration.ZERO, chordDuration.getKey());
        assertEquals(new Note(1, new Duration(1, 2)), chordDuration.getValue().getSingleNote(0));
        List<Note> additionalNotes = chordDuration.getValue().getNotes()[0].getAdditionalNotes();
        assertEquals(1, additionalNotes.size());
        assertEquals(new Note(2, new Duration(1, 2)), additionalNotes.get(0));
    }

    // Remove note
    @Test
    public void testRemoveNote() {
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, new TestDrawer(), testInput);

        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();
        compositionMediator.mousePressed(CompositionMouseAdapter.RIGHT_CLICK);

        assertEquals(0, composition.chordList().size());
    }

    @Test
    public void testRemoveTwoNotes() {
        TestCompositionProperties testProperties = new TestCompositionProperties();
        TestUserInput testInput = new TestUserInput(10, 20);
        Composition composition = new Composition();
        CompositionMediator compositionMediator = TestMediatorCreator.newMediator(composition, 0, 0, 160, 224, testProperties, new TestDrawer(), testInput);

        testInput.setMouseXY(20, 20);
        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        testProperties.selectedVoice = 1;
        testInput.setMouseXY(20, 40);
        compositionMediator.mousePressed(CompositionMouseAdapter.LEFT_CLICK);
        compositionMediator.mouseReleased();

        testInput.setMouseXY(20, 20);
        compositionMediator.mousePressed(CompositionMouseAdapter.RIGHT_CLICK);

        testInput.setMouseXY(20, 40);
        compositionMediator.mousePressed(CompositionMouseAdapter.RIGHT_CLICK);

        assertEquals(0, composition.chordList().size());
    }
}
