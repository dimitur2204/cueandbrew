package via.dk.cueandbrew.server.dao;

import via.dk.cueandbrew.model.Reservation;

import java.sql.SQLException;

public interface ReservationDao {
    Reservation create(String clientFirstName, String clientLastName, String clientPhoneNumber) throws SQLException;
    Reservation readByTable(int tableId) throws SQLException;
    void update(Reservation reservation) throws SQLException;
    void delete(Reservation reservation) throws SQLException;
}
