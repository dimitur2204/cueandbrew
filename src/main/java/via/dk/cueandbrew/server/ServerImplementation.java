package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import via.dk.cueandbrew.databse.dao.*;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ServerImplementation implements ServerInterface {

    private final RemotePropertyChangeSupport<Serializable> registrationSupport;
    private final RemotePropertyChangeSupport<Serializable> reservationSupport;

    public ServerImplementation() {
        this.registrationSupport = new RemotePropertyChangeSupport<>();
        this.reservationSupport = new RemotePropertyChangeSupport<>();
    }

    @Override
    public void onLogin(String login, String password, UUID id)
            throws RemoteException {
        try {
            Registration registration = RegistrationDaoImplementation.getInstance().getRegistration(login, password);
            if (registration != null) {
                registration.setId(id);
                //there is a login
                this.registrationSupport.firePropertyChange("login", null, registration);
            }
            else {
                //there isn't a login
                Registration registration1 = Registration.createAnEmptyRegistration();
                registration1.setId(id);
                this.registrationSupport.firePropertyChange("login", null, registration1);
            }
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
            Reservation res = ReservationDaoImpl.getInstance().create(builder);
            this.reservationSupport.firePropertyChange("reservation_created", null, res);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void addRegistrationPropertyChangeListener(
            RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
        this.registrationSupport.addPropertyChangeListener(listener);
    }

    public void addReservationPropertyChangeListener(
            RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
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

    @Override public void addDrink(String name, double price, int quantity)
        throws RemoteException
    {
        try
        {
          DrinksDaoImpl.getInstance().addDrink(name, price, quantity);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        try {
            return FeedbackDaoImplementation.getInstance().createFeedback(content, selectedType, firstname, lastname);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    @Override
    public void createNotification(Notification notification) throws RemoteException{
        try {
            NotificationDaoImpl.getInstance().createNotification(notification);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    @Override public boolean cancelReservation(int id) throws RemoteException
    {
        try
        {
            return ReservationDaoImpl.getInstance().cancelReservation(id);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        try {
            return NotificationDaoImpl.getInstance().fetchNotifications();
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        try {
            NotificationDaoImpl.getInstance().markNotificationAsRead(notification);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }
}
