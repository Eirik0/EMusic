package util;

import static org.junit.Assert.assertEquals;
import gui.properties.SelectableIsntrument;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FileUtilitiesTest {
    @Test
    public void testLoadFromStream() {
        SelectableIsntrument[] instruments = SelectableIsntrument.loadInstruments();
        assertEquals(128, instruments.length);
        assertEquals(0, instruments[0].number);
        assertEquals("Acoustic Grand Piano", instruments[0].name);
        assertEquals(52, instruments[52].number);
        assertEquals("Choir \"Aah\"", instruments[52].name);
    }

    @Test
    public void testLoadFromStream_Failure() {
        InputStream stream = FileUtilitiesTest.class.getResourceAsStream("/instruments/SelectableInstruments_NOTREAL.txt");
        List<SelectableIsntrument> instruments = FileUtilities.loadFromStream(stream,
                line -> null,
                () -> Collections.singletonList(new SelectableIsntrument(0, "error loading instrument list")));
        assertEquals(1, instruments.size());
        assertEquals(0, instruments.get(0).number);
        assertEquals("error loading instrument list", instruments.get(0).name);
    }
}
