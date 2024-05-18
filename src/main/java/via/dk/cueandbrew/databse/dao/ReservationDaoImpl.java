package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.*;
import via.dk.cueandbrew.databse.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the methods that are avaliable for interacting with the drinks in the database
 * @author Darja Jefremova, Dimitar Nizamov, Marius Marcoci
 */
public class ReservationDaoImpl implements ReservationDao {
    private static ReservationDaoImpl instance;

    private ReservationDaoImpl() {
    }


    /**
     * A method that is used to create an instance of the ReservationDaoImpl class
     * @return an instance of the ReservationDaoImpl class
     * @throws SQLException
     */
    public static ReservationDaoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReservationDaoImpl();
        }
        return instance;
    }

    /**
     * A method that is used to create a reservation in the database
     * @param builder the builder that is used to create the reservation
     * @return the reservation
     * @throws SQLException
     */
    @Override
    public Reservation create(Reservation.ReservationBuilder builder) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            Reservation res = builder.build();
            Booking booking = res.getBooking();
            PreparedStatement insertBooking = connection.prepareStatement("INSERT INTO cueandbrew.bookings (date, start_time, end_time) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertBooking.setDate(1, booking.getDate());
            insertBooking.setTime(2, booking.getStartTime());
            insertBooking.setTime(3, booking.getEndTime());
            insertBooking.executeUpdate();
            ResultSet bookingKeys = insertBooking.getGeneratedKeys();
            bookingKeys.next();
            int bookingId = bookingKeys.getInt(1);
            List<Table> tables = res.getBooking().getTables();
            for (Table table : tables) {
                PreparedStatement insertTable = connection.prepareStatement("INSERT INTO cueandbrew.booking_tables (booking_id, table_number) VALUES (?, ?)");
                insertTable.setInt(1, bookingId);
                insertTable.setInt(2, table.getNumber());
                insertTable.executeUpdate();
            }
            Order order = res.getOrder();
            int orderId = 0;
            if (order != null) {
                PreparedStatement insertOrder = connection.prepareStatement("INSERT INTO cueandbrew.orders (expected_order_date, expected_order_time) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                insertOrder.setDate(1, Date.valueOf(order.getExpectedDatetime().toLocalDateTime().toLocalDate()));
                insertOrder.setTime(2, Time.valueOf(order.getExpectedDatetime().toLocalDateTime().toLocalTime()));
                insertOrder.executeUpdate();
                ResultSet orderKeys = insertOrder.getGeneratedKeys();
                orderKeys.next();
                orderId = orderKeys.getInt(1);
            }
            if (order != null) {
                List<Drink> drinks = order.getDrinks();
                for (Drink drink : drinks) {
                    PreparedStatement insertDrink = connection.prepareStatement("INSERT INTO cueandbrew.order_drinks (order_id, drink_id) VALUES (?, ?)");
                    insertDrink.setInt(1, orderId);
                    insertDrink.setInt(2, drink.getId());
                    insertDrink.executeUpdate();
                }
            }
            PreparedStatement insertReservation = connection.prepareStatement("INSERT INTO cueandbrew.reservations (booking_id, order_id, client_firstname, client_lastname, client_phone_number, notes, creation_datetime) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertReservation.setInt(1, bookingId);
            if (orderId != 0) {
                insertReservation.setInt(2, orderId);
            } else {
                insertReservation.setNull(2, Types.INTEGER);
            }
            insertReservation.setString(3, res.getClientFirstName());
            insertReservation.setString(4, res.getClientLastName());
            insertReservation.setString(5, res.getClientPhoneNumber());
            insertReservation.setString(6, res.getNotes());
            insertReservation.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
            insertReservation.executeUpdate();
            ResultSet keys = insertReservation.getGeneratedKeys();
            keys.next();
            res.setReservationId(keys.getInt(1));
            return res;
        }
    }

    /**
     * A method that is to read a reservation from the database
     * @param id the id of the reservation
     * @return the reservation
     * @throws SQLException
     */
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
                return new Reservation.ReservationBuilder()
                        .setClientLastName(lastname)
                        .setClientFirstName(firstname)
                        .setClientPhoneNumber(phoneNumber)
                        .setNotes(result.getString("notes"))
                        .build();
            }
            return null;
        }
    }

    /**
     * A method that is used to read a reservation from the database
     * @param phone the phone number of the reservation
     * @return a list of reservations
     * @throws SQLException
     */
    @Override
    public List<Reservation> readByPhoneNumber(String phone)
            throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT
                        r.reservation_id,
                        r.client_firstname,
                        r.client_lastname,
                        r.client_phone_number,
                        r.notes,
                        r.creation_datetime,
                        r.order_id,
                        t.number,
                        d.drink_id,
                        d.name,
                        d.quantity,
                        d.price,
                        o.expected_order_date,
                        o.expected_order_time,
                        b.date,
                        b.end_time,
                        b.start_time
                    FROM
                        cueandbrew.reservations r
                    JOIN
                        cueandbrew.bookings b on r.booking_id = b.booking_id
                    JOIN
                        cueandbrew.booking_tables bt ON b.booking_id = bt.booking_id
                    JOIN
                        cueandbrew.tables t ON bt.table_number = t.number
                    LEFT JOIN
                        cueandbrew.orders o ON r.order_id = o.order_id
                    LEFT JOIN
                        cueandbrew.order_drinks od ON o.order_id = od.order_id
                    LEFT JOIN
                        cueandbrew.drinks d ON od.drink_id = d.drink_id
                    WHERE
                        r.client_phone_number = ?;
                    """);
            statement.setString(1, phone);
            try (var result = statement.executeQuery()) {
                ArrayList<Reservation> reservations = new ArrayList<>();
                ArrayList<Table> tables = new ArrayList<>();
                ArrayList<Drink> drinks = new ArrayList<>();
                Booking booking = new Booking();
                Order order = new Order();

                while (result.next()) {
                    if (reservations.isEmpty()) {
                        //ORDER
                        int order_id = result.getInt("order_id");
                        if (result.wasNull()) {
                            order_id = -1;
                        }
                        if (order_id != -1) {
                            String drinkName = result.getString("name");
                            int drinkId = result.getInt("drink_id");
                            double drinkPrice = result.getDouble("price");
                            int quantity = result.getInt("quantity");
                            String expected_order_date = result.getString("expected_order_date");
                            String expected_order_time = result.getString("expected_order_time");
                            drinks.add(new Drink(drinkId, drinkName, drinkPrice, quantity));
                            order.setDrinks(drinks);
                            order.setExpectedDatetime(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(expected_order_date, DateTimeFormatter.ISO_LOCAL_DATE), LocalTime.parse(expected_order_time, DateTimeFormatter.ISO_LOCAL_TIME))));
                        }

                        //BOOKING
                        int table_number = result.getInt("number");
                        tables.add(new Table(table_number));
                        String date = result.getString("date");
                        String start_time = result.getString("start_time");
                        String end_time = result.getString("end_time");
                        booking.setDate(Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)));
                        booking.setStartTime(Time.valueOf(LocalTime.parse(start_time, DateTimeFormatter.ISO_LOCAL_TIME)));
                        booking.setEndTime(Time.valueOf(LocalTime.parse(end_time, DateTimeFormatter.ISO_LOCAL_TIME)));
                        booking.setTables(tables);

                        //RESERVATION
                        int reservation_id = result.getInt("reservation_id");
                        String client_firstname = result.getString("client_firstname");
                        String client_lastname = result.getString("client_lastname");
                        String client_phone_number = result.getString("client_phone_number");
                        String notes = result.getString("notes");
                        String creation_datetime = result.getString("creation_datetime");
                        Reservation reservation = new Reservation.ReservationBuilder()
                                .setReservationId(reservation_id)
                                .setClientFirstName(client_firstname)
                                .setClientLastName(client_lastname)
                                .setClientPhoneNumber(client_phone_number)
                                .setNotes(notes)
                                .setCreationDatetime(Timestamp.valueOf(LocalDateTime.parse(creation_datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                                .setBooking(booking)
                                .setOrder(order)
                                .build();
                        reservations.add(reservation);
                    } else {
                        if (reservations.getLast().getReservationId() == result.getInt("reservation_id")) {
                            //table
                            Table table = new Table(result.getInt("number"));
                            if (!reservations.getLast().getBooking().containsTable(table.getNumber())) {
                                tables.add(table);
                            }
                            //drink
                            if (reservations.getLast().getOrder() != null) {
                                int drink_id = result.getInt("drink_id");
                                if (result.wasNull()) {
                                    drink_id = -1;
                                }
                                if (drink_id != -1) {
                                    Drink drink = new Drink(result.getInt("drink_id"), result.getString("name"), result.getDouble("price"), result.getInt("quantity"));
                                    if (!reservations.getLast().getOrder().containsDrink(drink.getName())) {
                                        drinks.add(drink);
                                    }
                                }
                                //add new drinks to order
                                reservations.getLast().getOrder().setDrinks(drinks);
                            }

                            //add new tables to booking
                            reservations.getLast().getBooking().setTables(tables);
                        } else {
                            //this is a completely new reservation -> clear tables, drinks, booking, order
                            //add the new reservation to the list
                            tables.clear();
                            drinks.clear();

                            order = new Order();
                            booking = new Booking();

                            //ORDER
                            int order_id = result.getInt("order_id");
                            if (result.wasNull()) {
                                order_id = -1;
                            }

                            if (order_id != -1) {
                                String drinkName = result.getString("name");
                                int drinkId = result.getInt("drink_id");
                                double drinkPrice = result.getDouble("price");
                                int quantity = result.getInt("quantity");
                                String expected_order_date = result.getString("expected_order_date");
                                String expected_order_time = result.getString("expected_order_time");
                                drinks.add(new Drink(drinkId, drinkName, drinkPrice, quantity));
                                order.setDrinks(drinks);
                                order.setExpectedDatetime(Timestamp.valueOf(LocalDateTime.of(LocalDate.parse(expected_order_date, DateTimeFormatter.ISO_LOCAL_DATE), LocalTime.parse(expected_order_time, DateTimeFormatter.ISO_LOCAL_TIME))));
                            }

                            //BOOKING
                            int table_number = result.getInt("number");
                            tables.add(new Table(table_number));
                            String date = result.getString("date");
                            String start_time = result.getString("start_time");
                            String end_time = result.getString("end_time");
                            booking.setDate(Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)));
                            booking.setStartTime(Time.valueOf(LocalTime.parse(start_time, DateTimeFormatter.ISO_LOCAL_TIME)));
                            booking.setEndTime(Time.valueOf(LocalTime.parse(end_time, DateTimeFormatter.ISO_LOCAL_TIME)));
                            booking.setTables(tables);

                            //RESERVATION
                            int reservation_id = result.getInt("reservation_id");
                            String client_firstname = result.getString("client_firstname");
                            String client_lastname = result.getString("client_lastname");
                            String client_phone_number = result.getString("client_phone_number");
                            String notes = result.getString("notes");
                            Timestamp creation_datetime = result.getTimestamp("creation_datetime");
                            Reservation reservation = new Reservation.ReservationBuilder()
                                    .setReservationId(reservation_id)
                                    .setClientFirstName(client_firstname)
                                    .setClientLastName(client_lastname)
                                    .setClientPhoneNumber(client_phone_number)
                                    .setNotes(notes)
                                    .setCreationDatetime(creation_datetime)
                                    .setBooking(booking)
                                    .setOrder(order)
                                    .build();
                            reservations.add(reservation);
                        }
                    }
                }
                return reservations;
            }
        }
    }

    /**
     * A method that is used to find reservations within a certain period of time
     * @param start the start time of the period
     * @param durationMinutes the duration of the period
     * @return a list of reservations
     * @throws SQLException
     */
    public List<Reservation> findReservationsWithinPeriod(LocalDateTime start, int durationMinutes) {
        List<Reservation> overlappingReservations = new ArrayList<>();
        LocalDateTime endTime = start.plusMinutes(durationMinutes);
        String sql = """
                SELECT r.*, b.*, t.*
                FROM cueandbrew.reservations AS r
                JOIN cueandbrew.bookings AS b ON r.booking_id = b.booking_id
                JOIN cueandbrew.booking_tables AS bt ON bt.booking_id = b.booking_id
                JOIN cueandbrew.tables AS t ON bt.table_number = t.number
                WHERE b.date = ? AND ((b.start_time BETWEEN ? AND ?)
                   OR (b.end_time BETWEEN ? AND ?)
                   OR (b.start_time <= ? AND b.end_time >= ?));
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
                            .setBooking(booking)
                            .build();
                    overlappingReservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return overlappingReservations;
    }

    /**
     * A method that is used to update a reservation in the database
     * @param reservation the reservation that is to be updated
     * @throws SQLException
     */
    public Reservation getReservationById(int id) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM cueandbrew.reservations WHERE reservation_id = ?;
                    """);
            statement.setInt(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                String firstname = result.getString("client_firstname");
                String lastname = result.getString("client_lastname");
                String phoneNumber = result.getString("client_phone_number");
                String bookingId = result.getString("booking_id");
                String orderId = result.getString("order_id");
                String notes = result.getString("notes");
                Reservation.ReservationBuilder reservation = new Reservation.ReservationBuilder()
                        .setClientFirstName(firstname)
                        .setClientLastName(lastname)
                        .setClientPhoneNumber(phoneNumber);
                List<Table> tables = new ArrayList<>();
                PreparedStatement bookingStatement = connection.prepareStatement("""
                        SELECT * FROM cueandbrew.booking_tables WHERE booking_id = ?;
                        """);
                bookingStatement.setInt(1, Integer.parseInt(bookingId));
                var bookingResult = bookingStatement.executeQuery();
                while (bookingResult.next()) {
                    tables.add(new Table(bookingResult.getInt("table_number")));
                }
                Booking booking = new Booking();
                PreparedStatement bookingStatement2 = connection.prepareStatement("""
                        SELECT date, start_time, end_time FROM cueandbrew.bookings WHERE booking_id = ?;
                        """);
                bookingStatement2.setInt(1, Integer.parseInt(bookingId));
                var bookingResult2 = bookingStatement2.executeQuery();
                bookingResult2.next();
                booking.setDate(bookingResult2.getDate("date"));
                booking.setStartTime(bookingResult2.getTime("start_time"));
                booking.setEndTime(bookingResult2.getTime("end_time"));
                booking.setTables(tables);
                Order order = null;
                if (orderId != null) {
                    order = new Order();
                    PreparedStatement orderStatement = connection.prepareStatement("""
                            SELECT * FROM cueandbrew.order_drinks WHERE order_id = ?;
                            """);
                    orderStatement.setInt(1, Integer.parseInt(orderId));
                    var orderResult = orderStatement.executeQuery();
                    List<Drink> drinks = new ArrayList<>();
                    while (orderResult.next()) {
                        PreparedStatement drinkStatement = connection.prepareStatement("""
                                SELECT * FROM cueandbrew.drinks WHERE drink_id = ?;
                                """);
                        drinkStatement.setInt(1, orderResult.getInt("drink_id"));
                        var drinkResult = drinkStatement.executeQuery();
                        drinkResult.next();
                        var drinkId = drinkResult.getInt("drink_id");
                        var name = drinkResult.getString("name");
                        var price = drinkResult.getDouble("price");
                        var quantity = drinkResult.getInt("quantity");
                        drinks.add(new Drink(drinkId, name, price, quantity));
                    }
                    PreparedStatement orderStatement2 = connection.prepareStatement("""
                            SELECT * FROM cueandbrew.orders WHERE order_id = ?;
                            """);
                    orderStatement2.setInt(1, Integer.parseInt(orderId));
                    var orderResult2 = orderStatement2.executeQuery();
                    orderResult2.next();
                    order.setExpectedDatetime(orderResult2.getTimestamp("expected_order_date"));
                    order.setDrinks(drinks);
                }
                reservation.setBooking(booking);
                reservation.setOrder(order);
                reservation.setNotes(notes);
                return reservation.build();
            }
        }
        return null;
    }

}
