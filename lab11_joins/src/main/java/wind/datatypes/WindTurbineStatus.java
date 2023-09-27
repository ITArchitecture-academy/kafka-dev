package wind.datatypes;

public class WindTurbineStatus {
    public int id;
    public boolean is_online;
    public String status;

    public WindTurbineStatus(int id, boolean is_online, String status) {
        this.id = id;
        this.is_online = is_online;
        this.status = status;
    }

    @Override
    public String toString() {
        return "WindTurbineStatus{" +
                "id=" + id +
                ", is_online=" + is_online +
                ", status='" + status + '\'' +
                '}';
    }
}
