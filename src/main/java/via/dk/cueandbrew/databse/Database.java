package via.dk.cueandbrew.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // You need to change the password and user to your own
    private static final String schema = "cueandbrew";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + schema;
    private static final String user = "postgres";
    private static final String password = "";
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
