package via.dk.cueandbrew.server;

import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Order;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.server.dao.ReservationDao;
import via.dk.cueandbrew.server.dao.ReservationDaoImpl;

import java.sql.*;
import java.util.ArrayList;

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
