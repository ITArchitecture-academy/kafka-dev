package wind;

public class WindTurbineEnergyGeneratedData {
    public String windTurbineId;
    public double generatedPowerInMwh;

    public WindTurbineEnergyGeneratedData(String windTurbineId, double generatedPowerInMwh) {
        this.windTurbineId = windTurbineId;
        this.generatedPowerInMwh = generatedPowerInMwh;
    }

    @Override
    public String toString() {
        return "WindTurbinePowerData{" +
                "windTurbineId='" + windTurbineId + '\'' +
                ", generatedPowerInMwh=" + generatedPowerInMwh +
                '}';
    }
}
