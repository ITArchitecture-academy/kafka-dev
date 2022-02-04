package example.common;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class TemperatureReadingSupplier implements Supplier<Map.Entry<String, TemperatureReading>> {
    private final static List<Sensor> temperatureSensors = List.of(
            new Sensor("Pharmacy", 5.0, true)
            , new Sensor("Production", 7.0, false)
    );

    private final static double MAX_TEMP = 10;
    private final static double MIN_TEMP = 2;
    private final Random rand = new Random();

    private final int msgsPerSec;
    private int msgCount;

    public TemperatureReadingSupplier(int msgsPerSec) {
        this.msgsPerSec = msgsPerSec;
        msgCount = 0;
    }

    @Override
    public Map.Entry<String, TemperatureReading> get() {
        Sensor currentSensor = temperatureSensors.get(rand.nextInt(temperatureSensors.size()));
        TemperatureReading temperatureReading = new TemperatureReading();
        boolean shouldOverheat = rand.nextDouble() < 0.1;
        if (currentSensor.shouldHeat) {
            double maxIncrease = MAX_TEMP - currentSensor.currentTemperature;
            double increase = rand.nextDouble() * maxIncrease;
            if (shouldOverheat) {
                increase += 0.5;
            }
            temperatureReading.temperature = currentSensor.currentTemperature + increase;
        } else {
            double maxDecrease = currentSensor.currentTemperature - MIN_TEMP;
            double decrease = rand.nextDouble() * maxDecrease;
            if (shouldOverheat) {
                decrease += 0.5;
            }
            temperatureReading.temperature = currentSensor.currentTemperature - decrease;
        }
        currentSensor.currentTemperature = temperatureReading.temperature;
        currentSensor.shouldHeat = !currentSensor.shouldHeat;

        temperatureReading.event_time_millis = System.currentTimeMillis() - rand.nextInt(5000);

        if (msgCount >= msgsPerSec && msgsPerSec != -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msgCount = 0;
        }
        msgCount++;
        return Map.entry(currentSensor.name, temperatureReading);
    }


    private static class Sensor {
        String name;
        double currentTemperature;
        boolean shouldHeat;

        public Sensor(String name, double currentTemperature, boolean shouldHeat) {
            this.name = name;
            this.currentTemperature = currentTemperature;
            this.shouldHeat = shouldHeat;
        }
    }
}
