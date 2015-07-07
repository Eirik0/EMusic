package util;

import music.Duration;

public class EMath {
	private static final int SMALLEST_DIVISION = 128 * 9 * 6;

	public static int round(double d) {
		return (int) Math.round(d);
	}

	public static long gcd(long a, long b) {
		long r = b == 0 ? a : a % b;
		while (r != 0) {
			a = b;
			b = r;
			r = a % b;
		}
		return b;
	}

	public static Duration guessDuration(double d) {
		long num = round(d * SMALLEST_DIVISION);
		long gcd = gcd(num, SMALLEST_DIVISION);
		return new Duration(num / gcd, SMALLEST_DIVISION / gcd);
	}
}
