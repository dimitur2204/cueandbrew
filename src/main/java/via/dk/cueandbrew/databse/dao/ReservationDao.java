package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Reservation;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDao {
    Reservation create(Reservation.ReservationBuilder builder) throws SQLException;
    Reservation readByTable(int tableId) throws SQLException;
    List<Reservation> readByPhoneNumber(String phone) throws SQLException;
    void update(Reservation reservation) throws SQLException;
    void delete(Reservation reservation) throws SQLException;
    List<Reservation> findReservationsWithinPeriod(LocalDateTime start, int durationMinutes) throws SQLException;
}
