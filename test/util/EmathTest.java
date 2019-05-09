package util;

import static org.junit.Assert.assertEquals;
import music.Duration;

import org.junit.Test;

public class EmathTest {
    @Test
    public void testRoundOddUp() {
        assertEquals(10, EMath.round(9.5));
    }

    @Test
    public void testRoundOddDown() {
        assertEquals(9, EMath.round(9.4999));
    }

    @Test
    public void testRoundEvenUp() {
        assertEquals(9, EMath.round(8.5));
    }

    @Test
    public void testRoundEvenDown() {
        assertEquals(8, EMath.round(8.4999));
    }

    @Test
    public void testRoundUpNeg() {
        assertEquals(-9, EMath.round(-9.5));
    }

    @Test
    public void testRoundDownNeg() {
        assertEquals(-10, EMath.round(-9.5001));
    }

    @Test
    public void testGcd_Zero() {
        assertEquals(15, EMath.gcd(0, 15));
        assertEquals(15, EMath.gcd(15, 0));
    }

    @Test
    public void testGcd_One() {
        assertEquals(1, EMath.gcd(15, 1));
        assertEquals(1, EMath.gcd(31, 37));
    }

    @Test
    public void testGcd_Two() {
        assertEquals(2, EMath.gcd(2, 4));
        assertEquals(2, EMath.gcd(64, 74));
    }

    @Test
    public void testGcd_Large() {
        assertEquals(2 * 3 * 5 * 7 * 7 * 11 * 13, EMath.gcd(2 * 2 * 3 * 5 * 7 * 7 * 7 * 11 * 13 * 37, 2 * 3 * 3 * 5 * 7 * 7 * 11 * 13 * 31));
    }

    @Test
    public void testGuessDuation_OneHalf() {
        assertEquals(new Duration(1, 2), EMath.guessDuration(1.0 / 2));
    }

    @Test
    public void testGuessDuation_OneThird() {
        assertEquals(new Duration(1, 3), EMath.guessDuration(1.0 / 3));
    }

    @Test
    public void testGuessDuation_Large() {
        assertEquals(new Duration(3999997, 192), EMath.guessDuration(3999997.0 / 192));
    }
}
