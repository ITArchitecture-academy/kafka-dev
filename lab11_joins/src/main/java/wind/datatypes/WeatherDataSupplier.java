package wind.datatypes;

import java.util.function.Supplier;

public class WeatherDataSupplier implements Supplier<WeatherData> {
    private static final String[] WINDDIRECTIONS = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    public WeatherDataSupplier() {

    }

    @Override
    public WeatherData get() {
        // Generate random weather data
        int turbineId = (int) (Math.random() * 100);
        double temperature = Math.random() * 50 - 25;
        double humidity = Math.random() * 100;
        double wind_speed = Math.random() * 100;
        String wind_direction = WINDDIRECTIONS[(int) (Math.random() * WINDDIRECTIONS.length)];
        double air_pressure = Math.random() * 100;

        return new WeatherData(turbineId, temperature, humidity, wind_speed, wind_direction, air_pressure);
    }
}
