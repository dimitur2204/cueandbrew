package via.dk.cueandbrew.server.dao;

import via.dk.cueandbrew.model.Reservation;
import via.dk.cueandbrew.server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDaoImpl implements ReservationDao {
    private static ReservationDaoImpl instance;

    private ReservationDaoImpl() throws SQLException {
    }

    public static ReservationDaoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReservationDaoImpl();
        }
        return instance;
    }

    @Override
    public Reservation create(String clientFirstName, String clientLastName, String clientPhoneNumber) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cueandbrew.reservation (client_firstname, client_lastname, client_phone_number, creation_datetime ) VALUES (?, ?, ?, ?)");
            Reservation res = new Reservation();
            statement.setString(1, clientFirstName);
            statement.setString(2, clientLastName);
            statement.setString(3, clientPhoneNumber);
            statement.setTimestamp(4, res.getCreationDatetime());
            return res;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Reservation readByTable(int tableId) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    select * from cueandbrew.reservations as r
                    join cueandbrew.bookings as b on r.booking_id = b.booking_id
                    join cueandbrew.tables as t on b.table_number = t.number
                    where t.number = ?;""");
            statement.setInt(1, tableId);
            var result = statement.executeQuery();
            if (result.next()) {
                Reservation res = new Reservation();
                res.setClientFirstName(result.getString("client_firstname"));
                res.setClientLastName(result.getString("client_lastname"));
                res.setClientPhoneNumber(result.getString("client_phone_number"));
                res.setCreationDatetime(result.getTimestamp("creation_datetime"));
                return res;
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void update(Reservation reservation) throws SQLException {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }
}
