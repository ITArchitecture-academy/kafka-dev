package wind.datatypes;

public class WindTurbineStatusWithMasterdata {
    public WindTurbineStatus turbineStatus;
    public WindTurbineMasterData masterData;

    public WindTurbineStatusWithMasterdata(WindTurbineStatus turbineStatus, WindTurbineMasterData masterData) {
        this.turbineStatus = turbineStatus;
        this.masterData = masterData;
    }

    @Override
    public String toString() {
        return "WindTurbineStatusWithMasterdata{" +
                "turbineStatus=" + turbineStatus +
                ", masterData=" + masterData +
                '}';
    }
}
