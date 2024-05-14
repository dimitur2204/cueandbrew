package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import via.dk.cueandbrew.databse.dao.RegistrationDaoImplementation;
import via.dk.cueandbrew.databse.dao.ReservationDaoImpl;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ServerImplementation implements ServerInterface {
    private final RemotePropertyChangeSupport<Registration> support;

    public ServerImplementation() {
        this.support = new RemotePropertyChangeSupport<>();
    }

    @Override
    public void onLogin(String login, String password)
            throws RemoteException {
        try {
            RegistrationDaoImplementation dao = RegistrationDaoImplementation.getInstance();
            Registration temp = new Registration();
            this.support.firePropertyChange("login", temp, dao.getRegistration(login, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        try {
            return ReservationDaoImpl.getInstance().findReservationsWithinPeriod(start, durationMinutes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addPropertyChangeListener(
            RemotePropertyChangeListener<Registration> listener) throws RemoteException {
        this.support.addPropertyChangeListener(listener);
    }

}
