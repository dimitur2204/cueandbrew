package via.dk.cueandbrew.databse;

import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.databse.dao.ReservationDao;
import via.dk.cueandbrew.databse.dao.ReservationDaoImpl;

import java.sql.*;

public class Start {
    public static void main(String[] args) {
        try {
            ReservationDao reservationDao = ReservationDaoImpl.getInstance();
            Reservation.ReservationBuilder builder = new Reservation.ReservationBuilder("Dimitar", "Nizamov", "+45527124134")
                    .setNotes("This is a test");
            reservationDao.create(builder);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
