package wind;

// See https://en.wikipedia.org/wiki/Moving_average how to calculate the Sliding Average
class SlidingAverage {
    double sumPower = 0;
    long numDataPoints = 0;
    double average = 0;

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
