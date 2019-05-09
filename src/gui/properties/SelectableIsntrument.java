package gui.properties;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import util.FileUtilities;

public class SelectableIsntrument {
    public static final SelectableIsntrument[] INSTRUMENTS = loadInstruments();

    public final int number;
    public final String name;

    public SelectableIsntrument(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }

    public static SelectableIsntrument[] loadInstruments() {
        InputStream stream = SelectableIsntrument.class.getResourceAsStream("/instruments/SelectableInstruments.txt");
        List<SelectableIsntrument> instruments = FileUtilities.loadFromStream(stream, line -> {
            String[] split = line.split(",");
            return new SelectableIsntrument(Integer.valueOf(split[0]), split[1]);
        }, () -> Collections.singletonList(new SelectableIsntrument(0, "error loading instrument list")));
        return instruments.toArray(new SelectableIsntrument[instruments.size()]);
    }
}
