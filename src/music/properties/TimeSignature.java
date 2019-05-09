package music.properties;

import music.Duration;

public class TimeSignature {
	private Duration meter;
	private Duration noteDuration = Duration.QUARTER_BEAT;
	private Duration division = new Duration(1, 1);

	public TimeSignature(int upper, int lower) {
		meter = new Duration(upper, lower);
	}

	public Duration getMeter() {
		return meter;
	}

	public void setMeter(int upper, int lower) {
		meter = new Duration(upper, lower);
	}

	public void setMeter(Duration meter) {
		this.meter = meter;
	}

	public Duration getDurationOfBar() {
		return meter;
	}

	public Duration getDurationOfBeat() {
		return new Duration(1, meter.division);
	}

	public Duration getNoteDuration() {
		return noteDuration;
	}

	public void setNoteDuration(Duration noteDuration) {
		this.noteDuration = noteDuration;
	}

	public Duration getDivision() {
		return division;
	}

	public void setDivision(Duration division) {
		this.division = division;
	}

	public Duration getCalculatedDivision() {
		return new Duration(noteDuration.beat * division.beat, noteDuration.division * division.division);
	}
}
