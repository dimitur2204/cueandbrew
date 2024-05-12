package via.dk.cueandbrew.server;

import via.dk.cueandbrew.model.Reservation;
import via.dk.cueandbrew.server.dao.ReservationDao;
import via.dk.cueandbrew.server.dao.ReservationDaoImpl;

import java.sql.*;

public class Start {
    public static void main(String[] args) {
        try {
            ReservationDao reservationDao = ReservationDaoImpl.getInstance();
            Reservation res = reservationDao.readByTable(1);
            System.out.println(res.getCreationDatetime());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
