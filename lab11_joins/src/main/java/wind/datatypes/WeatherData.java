package wind.datatypes;

public class WeatherData {
    public int turbineId;
    public double temperature;
    public double humidity;
    public double wind_speed;
    public String wind_direction;
    public double air_pressure;

    public WeatherData(int turbineId, double temperature, double humidity, double wind_speed, String wind_direction, double air_pressure) {
        this.turbineId = turbineId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.air_pressure = air_pressure;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "turbineId=" + turbineId +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", wind_speed=" + wind_speed +
                ", wind_direction=" + wind_direction +
                ", air_pressure=" + air_pressure +
                '}';
    }
}
