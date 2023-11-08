package wind;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MaintenanceLogs {


    private static final String INSERT_MAINTENANCE_SQL =
            "INSERT INTO maintenance_logs (turbine_id, date, actions_performed, next_maintenance_date, maintenance_costs, remarks) " +
                    "VALUES (?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?);";

    private static final String INSERT_OUTBOX_SQL =
            "INSERT INTO debezium_outbox (aggregatetype, aggregateid, type, payload, payloadid) " +
                    "VALUES (?, ?, ?, ?::json, ?);";

    private static final String URL = "jdbc:postgresql://localhost:5432/user";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

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
        // Define date format for SQL timestamp
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Generate random data
        int turbineId = ThreadLocalRandom.current().nextInt(1, 100); // Assuming you have 100 turbines
        String[] actions = {"Oil change", "Blade inspection", "Gearbox replacement"};
        String actionsPerformed = actions[ThreadLocalRandom.current().nextInt(actions.length)];
        double maintenanceCosts = ThreadLocalRandom.current().nextDouble(1000, 5000);
        String remarks = "Performed by technician #" + ThreadLocalRandom.current().nextInt(1, 10);
        LocalDateTime nextMaintenance = LocalDateTime.now().plusMonths(ThreadLocalRandom.current().nextInt(1, 12));
        String nextMaintenanceDate = nextMaintenance.format(dateTimeFormatter);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement maintenanceStmt = conn.prepareStatement(INSERT_MAINTENANCE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement outboxStmt = conn.prepareStatement(INSERT_OUTBOX_SQL)) {

            // Insert maintenance log
            maintenanceStmt.setInt(1, turbineId);
            maintenanceStmt.setString(2, actionsPerformed);
            maintenanceStmt.setDouble(3, maintenanceCosts);
            maintenanceStmt.setString(4, remarks);
            int affectedRows = maintenanceStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating maintenance log failed, no rows affected.");
            }

            // Retrieve maintenance log id
            int maintenanceId;
            try (ResultSet generatedKeys = maintenanceStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    maintenanceId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating maintenance log failed, no ID obtained.");
                }
            }

            // Insert outbox event
            String aggregatetype = "WindTurbine";
            String aggregateid = String.valueOf(turbineId);
            String type = "MaintenancePerformed";
            String payload = String.format("{\"turbineId\": %d, \"action\": \"%s\", \"nextMaintenance\": \"%s\"}",
                    turbineId, actionsPerformed, nextMaintenanceDate);
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
