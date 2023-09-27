package wind.datatypes;

public class WindTurbineMasterData {
    public int id;
    public String customer_name;
    public String wind_park_name;

    public WindTurbineMasterData(int id, String customer_name, String wind_park_name) {
        this.id = id;
        this.customer_name = customer_name;
        this.wind_park_name = wind_park_name;
    }

    @Override
    public String toString() {
        return "WindTurbineMasterData{" +
                "id=" + id +
                ", customer_name='" + customer_name + '\'' +
                ", wind_park_name='" + wind_park_name + '\'' +
                '}';
    }
}
