package music;

import util.EMath;

public class Duration implements Comparable<Duration> {
	public static final Duration ZERO = new Duration(0, 1);
	public static final Duration QUARTER_BEAT = new Duration(1, 4);
	public static final Duration HALF_BEAT = new Duration(1, 2);
	public static final Duration WHOLE_BEAT = new Duration(1, 1);

	public final long beat;
	public final long division;

	public Duration(long beat, long division) {
		this.beat = beat;
		this.division = division;
	}

	public Duration add(Duration addend) {
		long num = beat * addend.division + addend.beat * division;
		long den = division * addend.division;
		long gcd = EMath.gcd(num, den);
		return new Duration(num / gcd, den / gcd);
	}

	public Duration subtract(Duration subtrahend) {
		long num = beat * subtrahend.division - subtrahend.beat * division;
		long den = division * subtrahend.division;
		long gcd = EMath.gcd(num, den);
		return new Duration(num / gcd, den / gcd);
	}

	public double doubleValue() {
		return (double) beat / division;
	}

	@Override
	public int compareTo(Duration o) {
		return (int) (beat * o.division - o.beat * division);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * (prime + (int) (beat ^ (beat >>> 32))) + (int) (division ^ (division >>> 32));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Duration other = (Duration) obj;
		return beat * other.division == other.beat * division;
	}

	@Override
	public String toString() {
		return beat + "/" + division;
	}

	public static Duration max(Duration d1, Duration d2) {
		return d1.compareTo(d2) < 0 ? d2 : d1;
	}
}
