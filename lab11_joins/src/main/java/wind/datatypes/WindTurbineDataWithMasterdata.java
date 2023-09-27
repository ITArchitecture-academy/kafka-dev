package wind.datatypes;

import wind.WindTurbineData;

public class WindTurbineDataWithMasterdata {
    public WindTurbineData turbineData;
    public WindTurbineMasterData masterData;

    public WindTurbineDataWithMasterdata(WindTurbineData turbineData, WindTurbineMasterData masterData) {
        this.turbineData = turbineData;
        this.masterData = masterData;
    }

    @Override
    public String toString() {
        return "WindTurbineDataWithMasterdata{" +
                "turbineData=" + turbineData +
                ", masterData=" + masterData +
                '}';
    }
}
