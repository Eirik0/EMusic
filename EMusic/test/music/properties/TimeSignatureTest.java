package music.properties;

import static org.junit.Assert.assertEquals;
import music.Duration;
import music.properties.TimeSignature;

import org.junit.Test;

public class TimeSignatureTest {
	@Test
	public void testDurationOfBar() {
		assertEquals(new Duration(3, 4), new TimeSignature(3, 4).getDurationOfBar());
	}

	@Test
	public void testDurationOfBeat() {
		assertEquals(new Duration(1, 4), new TimeSignature(3, 4).getDurationOfBeat());
		assertEquals(new Duration(1, 4), new TimeSignature(4, 4).getDurationOfBeat());
		assertEquals(new Duration(1, 5), new TimeSignature(1, 5).getDurationOfBeat());
		assertEquals(new Duration(1, 5), new TimeSignature(6, 5).getDurationOfBeat());
	}

	@Test
	public void testDurationOfDivision() {
		TimeSignature timeSignature = new TimeSignature(4, 4);
		timeSignature.setNoteDuration(new Duration(1, 16));
		timeSignature.setDivision(new Duration(1, 4));
		assertEquals(new Duration(1, 64), timeSignature.getCalculatedDivision());
		timeSignature.setDivision(new Duration(1, 3));
		assertEquals(new Duration(1, 48), timeSignature.getCalculatedDivision());
		timeSignature.setNoteDuration(new Duration(1, 12));
		timeSignature.setDivision(new Duration(1, 4));
		assertEquals(new Duration(1, 48), timeSignature.getCalculatedDivision());
		timeSignature.setDivision(new Duration(1, 3));
		assertEquals(new Duration(1, 36), timeSignature.getCalculatedDivision());
	}

	@Test
	public void testDurationOfDivisionWithWeirdNumbers() {
		TimeSignature timeSignature = new TimeSignature(5, 5);
		timeSignature.setNoteDuration(new Duration(2, 15));
		timeSignature.setDivision(new Duration(3, 5));
		assertEquals(new Duration(6, 75), timeSignature.getCalculatedDivision());
	}
}
