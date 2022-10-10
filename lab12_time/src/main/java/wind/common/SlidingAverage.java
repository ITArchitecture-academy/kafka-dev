package wind.common;

public class SlidingAverage {
    public double sumPower = 0;
    public long numDataPoints = 0;
    public double average = 0;

    public SlidingAverage() {
    }

    public SlidingAverage increment(double data) {
        SlidingAverage result = new SlidingAverage();
        result.sumPower = this.sumPower + data;
        result.numDataPoints = this.numDataPoints + 1;
        result.average = result.sumPower / result.numDataPoints;
        return result;
    }
}
