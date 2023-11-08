package wind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MaintenanceLogs {


    private static final String INSERT_MAINTENANCE_SQL =
            "INSERT INTO maintenance_logs (turbine_id, date, actions_performed, next_maintenance_date, maintenance_costs, remarks) " +
                    "VALUES (?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?);";

    private static final String INSERT_OUTBOX_SQL =
            "INSERT INTO debezium_outbox (aggregatetype, aggregateid, type, payload, payloadid) " +
                    "VALUES (?, ?, ?, ?::json, ?);";

    private static final String URL = "jdbc:postgresql://localhost:5432/user";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";

    public static void main(String[] args) {
        MaintenanceLogs inserter = new MaintenanceLogs();
        int insertsPerSecond = 1; // Configure the number of inserts per second here.

        while (true) {
            inserter.insertData();
            try {
                Thread.sleep(1000 / insertsPerSecond);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

    public void insertData() {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement maintenanceStmt = conn.prepareStatement(INSERT_MAINTENANCE_SQL);
             PreparedStatement outboxStmt = conn.prepareStatement(INSERT_OUTBOX_SQL)) {

            // Example values, these should be dynamically generated or queried
            int turbineId = 1;
            String actionsPerformed = "Oil change";
            double maintenanceCosts = 1500.00;
            String remarks = "Regular maintenance performed";

            // Insert maintenance log
            maintenanceStmt.setInt(1, turbineId);
            maintenanceStmt.setString(2, actionsPerformed);
            maintenanceStmt.setDouble(3, maintenanceCosts);
            maintenanceStmt.setString(4, remarks);
            maintenanceStmt.executeUpdate();

            // Insert outbox event
            String aggregatetype = "WindTurbine";
            String aggregateid = String.valueOf(turbineId);
            String type = "MaintenancePerformed";
            String payload = "{\"turbineId\": \"" + turbineId + "\", \"action\": \"" + actionsPerformed + "\"}";
            UUID payloadid = UUID.randomUUID();

            outboxStmt.setString(1, aggregatetype);
            outboxStmt.setString(2, aggregateid);
            outboxStmt.setString(3, type);
            outboxStmt.setString(4, payload);
            outboxStmt.setObject(5, payloadid);
            outboxStmt.executeUpdate();

            // print success
            System.out.println("Inserted maintenance log and outbox event for turbine " + turbineId + ".");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
