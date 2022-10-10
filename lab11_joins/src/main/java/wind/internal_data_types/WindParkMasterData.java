package wind.internal_data_types;

public class WindParkMasterData {
    public int id;
    public String name;

    public WindParkMasterData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WindParkMasterData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
