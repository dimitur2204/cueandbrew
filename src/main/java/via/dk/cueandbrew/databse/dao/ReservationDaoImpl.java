package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.databse.Database;

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
    public Reservation create(Reservation.ReservationBuilder builder) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cueandbrew.reservations (client_firstname, client_lastname, client_phone_number, creation_datetime, notes ) VALUES (?, ?, ?, ?, ?)");
            Reservation res = builder.build();
            statement.setString(1, res.getClientFirstName());
            statement.setString(2, res.getClientLastName());
            statement.setString(3, res.getClientPhoneNumber());
            statement.setTimestamp(4, res.getCreationDatetime());
            statement.setString(5, res.getNotes());
            boolean execute = statement.execute();
            System.out.println(execute);
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
                String firstname = result.getString("client_firstname");
                String lastname = result.getString("client_lastname");
                String phoneNumber = result.getString("client_phone_number");
                Reservation res = new Reservation.ReservationBuilder(firstname, lastname, phoneNumber)
                        .setNotes(result.getString("notes"))
                        .build();
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
