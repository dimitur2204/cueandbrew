package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import via.dk.cueandbrew.databse.dao.FeedbackDaoImplementation;
import via.dk.cueandbrew.databse.dao.RegistrationDaoImplementation;
import via.dk.cueandbrew.databse.dao.ReservationDaoImpl;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ServerImplementation implements ServerInterface {

    private final RemotePropertyChangeSupport<Registration> registrationSupport;
    private final RemotePropertyChangeSupport<Reservation> reservationSupport;

    public ServerImplementation() {
        this.registrationSupport = new RemotePropertyChangeSupport<>();
        this.reservationSupport = new RemotePropertyChangeSupport<>();
    }

    @Override
    public void onLogin(String login, String password)
            throws RemoteException {
        try {
            RegistrationDaoImplementation dao = RegistrationDaoImplementation.getInstance();
            Registration temp = new Registration();
            this.registrationSupport.firePropertyChange("login", temp, dao.getRegistration(login, password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        try {
            return ReservationDaoImpl.getInstance().findReservationsWithinPeriod(start, durationMinutes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException {
        try {
            ReservationDaoImpl.getInstance().create(builder);
            Reservation res = builder.build();
            this.reservationSupport.firePropertyChange("reservation_created", null, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRegistrationPropertyChangeListener(
            RemotePropertyChangeListener<Registration> listener) throws RemoteException {
        this.registrationSupport.addPropertyChangeListener(listener);
    }

    public void addReservationPropertyChangeListener(
            RemotePropertyChangeListener<Reservation> listener) throws RemoteException {
        this.reservationSupport.addPropertyChangeListener(listener);
    }

    @Override
    public List<Reservation> onSearch(String phone) throws RemoteException {
        try {
            return ReservationDaoImpl.getInstance().readByPhoneNumber(phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        try {
            return FeedbackDaoImplementation.getInstance().createFeedback(content, selectedType, firstname, lastname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
