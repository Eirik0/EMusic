package music;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import music.Duration;

import org.junit.Test;

public class DurationTest {
	@Test
	public void testEquals() {
		assertEquals(new Duration(1, 4), new Duration(2, 8));
	}

	@Test
	public void testCompareEquality() {
		assertTrue(new Duration(1, 4).compareTo(new Duration(2, 8)) == 0);
	}

	@Test
	public void testCompareLess() {
		assertTrue(new Duration(1, 4).compareTo(new Duration(3, 8)) < 0);
	}

	@Test
	public void testCompareGreater() {
		assertTrue(new Duration(3, 4).compareTo(new Duration(2, 8)) > 0);
	}

	@Test
	public void testAdd() {
		assertEquals(new Duration(3, 4), new Duration(1, 4).add(new Duration(1, 2)));
	}

	@Test
	public void testDoubleValue() {
		assertEquals(0.25, new Duration(1, 4).doubleValue(), 0.000001);
		assertEquals(0.333333, new Duration(1, 3).doubleValue(), 0.000001);
		assertEquals(12345.666667, new Duration(1185184, 96).doubleValue(), 0.000001);
	}
}
