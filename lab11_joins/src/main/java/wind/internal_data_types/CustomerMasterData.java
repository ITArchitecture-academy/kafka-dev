package wind.internal_data_types;

public class CustomerMasterData {
    public int id;
    public String name;
    public double price_per_mwh;

    public CustomerMasterData(int id, String name, double price_per_mwh) {
        this.id = id;
        this.name = name;
        this.price_per_mwh = price_per_mwh;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price_per_mwh=" + price_per_mwh +
                '}';
    }
}
