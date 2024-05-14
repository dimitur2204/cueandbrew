package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Table;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                Reservation res = new Reservation.ReservationBuilder()
                        .setClientLastName(lastname)
                        .setClientFirstName(firstname)
                        .setClientPhoneNumber(phoneNumber)
                        .setNotes(result.getString("notes"))
                        .build();
                return res;
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override public List<Reservation> readByPhoneNumber(String phone)
        throws SQLException
    {
        try(Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                select r.notes, r.client_firstname, r.client_lastname, r.creation_time, r.client_phone_number
                from cueandbrew.reservations r
                inner join orders o on r.order_id = o.order_id
                inner join drinks d on d.drink_id = o.order_id
                inner join bookings b on b.booking_id = r.booking_id
                inner join tables t on t.number = b.table_number
                where r.client_phone_number = ?;
                """);
            statement.setString(1, phone);
            try(var result = statement.executeQuery()) {
                ArrayList<Reservation> reservations = new ArrayList<>();
                while (result.next()) {
                    String firstname = result.getString("client_firstname");
                    String lastname = result.getString("client_lastname");
                    String phoneNumber = result.getString("client_phone_number");
                    String date = result.getString("date");
                    String startTime = result.getString("start_time");
                    String endTimeStr = result.getString("end_time");
                    String tableNumber = result.getString("table_number");
                    Booking booking = new Booking(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTimeStr));
                    booking.getTables().add(new Table(Integer.parseInt(tableNumber)));
                    ArrayList<Booking> bookings = new ArrayList<>();
                    bookings.add(booking);
                    Reservation reservation = new Reservation.ReservationBuilder(firstname, lastname, phoneNumber)
                        .setNotes(result.getString("notes"))
                        .setBooking(bookings)
                        .build();
                    reservations.add(reservation);
                }
                return reservations;
            }
        }
    }

    public List<Reservation> findReservationsWithinPeriod(LocalDateTime start, int durationMinutes) {
        List<Reservation> overlappingReservations = new ArrayList<>();
        LocalDateTime endTime = start.plusMinutes(durationMinutes);
        String sql = """
                SELECT r.*, b.*, t.*
                FROM cueandbrew.reservations AS r
                JOIN cueandbrew.bookings AS b ON r.booking_id = b.booking_id
                JOIN cueandbrew.booking_tables AS bt ON bt.table_number = b.booking_id
                JOIN cueandbrew.tables AS t ON bt.table_number = t.number
                WHERE b.date = ? AND ((b.start_time BETWEEN ? AND ?)
                   OR (b.end_time BETWEEN ? AND ?)
                   OR (b.start_time < ? AND b.end_time > ?));
                """;

        try (Connection connection = Database.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(start.toLocalDate()));
            statement.setTime(2, Time.valueOf(start.toLocalTime()));
            statement.setTime(3, Time.valueOf(endTime.toLocalTime()));
            statement.setTime(4, Time.valueOf(start.toLocalTime()));
            statement.setTime(5, Time.valueOf(endTime.toLocalTime()));
            statement.setTime(6, Time.valueOf(start.toLocalTime()));
            statement.setTime(7, Time.valueOf(endTime.toLocalTime()));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String firstname = result.getString("client_firstname");
                    String lastname = result.getString("client_lastname");
                    String phoneNumber = result.getString("client_phone_number");
                    String date = result.getString("date");
                    String startTime = result.getString("start_time");
                    String endTimeStr = result.getString("end_time");
                    String tableNumber = result.getString("number");
                    Booking booking = new Booking(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTimeStr));
                    booking.getTables().add(new Table(Integer.parseInt(tableNumber)));
                    Reservation reservation = new Reservation.ReservationBuilder()
                            .setClientFirstName(firstname)
                            .setClientLastName(lastname)
                            .setClientPhoneNumber(phoneNumber)
                            .setNotes(result.getString("notes"))
                            .addBooking(booking)
                            .build();
                    overlappingReservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overlappingReservations;
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        if (reservation == null || reservation.getBookingId() == 0) {
            throw new IllegalArgumentException("Invalid reservation or booking ID");
        }
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cueandbrew.reservations SET client_firstname = ?, client_lastname = ?, client_phone_number = ?, notes = ? WHERE booking_id = ?");
            statement.setString(1, reservation.getClientFirstName());
            statement.setString(2, reservation.getClientLastName());
            statement.setString(3, reservation.getClientPhoneNumber());
            statement.setString(4, reservation.getNotes());
            statement.setInt(5, reservation.getBookingId());
        } catch (SQLException e) {
            System.err.println("SQL error during update: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Reservation reservation) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cueandbrew.reservations WHERE booking_id = ?");
            statement.setInt(1, reservation.getBookingId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
