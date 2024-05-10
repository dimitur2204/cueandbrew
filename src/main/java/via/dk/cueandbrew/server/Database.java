package via.dk.cueandbrew.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String schema = "cueandbrew";
    private static final String url = "jdbc:postgresql://localhost:5432/cueandbrew?currentSchema=" + schema;
    private static final String user = "dimitar.nizamov";
    private static final String password = "";
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
