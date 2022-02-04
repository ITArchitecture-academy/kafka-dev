package example.common;

public class SlidingAverage {
    public double average;
    public double sum;
    public int count;

    public SlidingAverage(double average, double sum, int count) {
        this.average = average;
        this.sum = sum;
        this.count = count;
    }

    @Override
    public String toString() {
        return "SlidingAverage{" +
                "average=" + average +
                ", sum=" + sum +
                ", count=" + count +
                '}';
    }
}
