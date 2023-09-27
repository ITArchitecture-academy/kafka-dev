package wind.datatypes;

import wind.WindTurbineData;

public class WindTurbineDataWithWeatherData {
    public WindTurbineData turbineData;
    public WeatherData weatherData;

    public WindTurbineDataWithWeatherData(WindTurbineData turbineData, WeatherData weatherData) {
        this.turbineData = turbineData;
        this.weatherData = weatherData;
    }

    @Override
    public String toString() {
        return "WindTurbineDataWithWeatherData{" +
                "turbineData=" + turbineData +
                ", weatherData=" + weatherData +
                '}';
    }
}
