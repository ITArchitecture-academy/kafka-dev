package example.common;

public class TemperatureReading {
    public long event_time_millis;
    public double temperature;

    @Override
    public String toString() {
        return "TemperatureReading{" +
                "event_time_millis=" + event_time_millis +
                ", temperature=" + temperature +
                '}';
    }
}
