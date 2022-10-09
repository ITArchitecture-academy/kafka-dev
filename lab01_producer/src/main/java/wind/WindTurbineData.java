package wind;

public class WindTurbineData{
    public String windTurbineId;
    public String windParkId;
    public double currentPower;

    public WindTurbineData(String windTurbineId, String windParkId, double currentPower) {
        this.windTurbineId = windTurbineId;
        this.windParkId = windParkId;
        this.currentPower = currentPower;
    }

    @Override
    public String toString() {
        return "WindTurbineData{" +
                "windTurbineId='" + windTurbineId + '\'' +
                ", windParkId='" + windParkId + '\'' +
                ", currentPower=" + currentPower +
                '}';
    }
}
