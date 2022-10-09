package wind;

import com.sun.net.httpserver.HttpServer;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// shows the current turbine data on `/`
public class WindTurbineAPI implements Closeable {
    private final HttpServer server;
    private final Map<String, WindTurbineData> measurements;

    public WindTurbineAPI(int port, Map<String, WindTurbineData> measurements) throws IOException {
        this.measurements = measurements;

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", httpExchange -> {
            try {
                byte[] responseBytes = getMetrics().getBytes();
                httpExchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
                byte[] responseBytes = "An error occured".getBytes(StandardCharsets.UTF_8);
                httpExchange.sendResponseHeaders(500, responseBytes.length);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            }

        });
        new Thread(server::start).start();
        System.out.println("Listening on port " + port);
    }

    private String getMetrics() {
        StringBuilder response = new StringBuilder();
        response.append("""
                # HELP power The current power of a wind turbine in W
                # TYPE power counter
                    """);
        for (WindTurbineData data : measurements.values()) {
            response.append("%1$s{turbine=%2$s,windPark=%3$s} %4$.02f\n"
                    .formatted("power", data.windTurbineId, data.windParkId, data.currentPower));
        }
        return response.toString();
    }


    @Override
    public void close() {
        server.stop(1);
    }
}
