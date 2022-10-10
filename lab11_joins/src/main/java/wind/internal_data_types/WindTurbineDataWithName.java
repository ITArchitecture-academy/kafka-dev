package wind.internal_data_types;

public class WindTurbineDataWithName {
    public String windTurbineId;
    public String windParkName;
    public double currentPower;

    public WindTurbineDataWithName(String windTurbineId, String windParkName, double currentPower) {
        this.windTurbineId = windTurbineId;
        this.windParkName = windParkName;
        this.currentPower = currentPower;
    }

    @Override
    public String toString() {
        return "WindTurbineDataWithName{" +
                "windTurbineId='" + windTurbineId + '\'' +
                ", windParkName='" + windParkName + '\'' +
                ", currentPower=" + currentPower +
                '}';
    }
}
