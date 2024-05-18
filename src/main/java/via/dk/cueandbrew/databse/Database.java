package via.dk.cueandbrew.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that is responsible for handling the database connection
 * @author Dimitar Nizamov
 */
public class Database {
    // You need to change the password and user to your own
    private static final String schema = "cueandbrew";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=" + schema;
    private static final String user = "postgres";
    private static final String password = "";
    /**
     * A method that creates a connection to the database with the specified url, user and password
     * @return The connection to the database
     */
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
