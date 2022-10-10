package wind.internal_data_types;

public class WindTurbineMasterData {
    public int id;
    public int customer_id;
    public String customer_name;
    public int wind_park_id;
    public String wind_park_name;

    public WindTurbineMasterData(int id, int customer_id, int wind_park_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.wind_park_id = wind_park_id;
    }

    public WindTurbineMasterData(WindTurbineMasterData old, CustomerMasterData customer) {
        this.id = old.id;
        this.customer_id = old.customer_id;
        this.customer_name = customer.name;
        this.wind_park_id = old.wind_park_id;
    }
    public WindTurbineMasterData(WindTurbineMasterData old, WindParkMasterData windPark) {
        this.id = old.id;
        this.customer_id = old.customer_id;
        this.customer_name = old.customer_name;
        this.wind_park_id = old.wind_park_id;
        this.wind_park_name = windPark.name;
    }


    @Override
    public String toString() {
        return "WindTurbineRawMasterData{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", wind_park_id=" + wind_park_id +
                '}';
    }
}
