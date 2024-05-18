package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import via.dk.cueandbrew.databse.dao.FeedbackDaoImplementation;
import via.dk.cueandbrew.databse.dao.NotificationDaoImpl;
import via.dk.cueandbrew.databse.dao.RegistrationDaoImplementation;
import via.dk.cueandbrew.databse.dao.ReservationDaoImpl;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * A class that is responsible for the Server Implementation implementing the Server Interface
 * It is responsible for the implementation of the methods that the client can call on the server
 * @author Marius Marcoci, Dimitar Nizamov, Darja Jefremova, Andreea Caisim
 */
public class ServerImplementation implements ServerInterface {

    private final RemotePropertyChangeSupport<Serializable> registrationSupport;
    private final RemotePropertyChangeSupport<Serializable> reservationSupport;

    /**
     * A constructor that initializes the Server Implementation
     */
    public ServerImplementation() {
        this.registrationSupport = new RemotePropertyChangeSupport<>();
        this.reservationSupport = new RemotePropertyChangeSupport<>();
    }

    /**
     * A method that is called when the client logs in
     * Checks the database if the login and password are correct
     * @param login The login of the client
     * @param password The password of the client
     * @param id The id of the client
     */
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

    /**
     * A method that returns the reservations in the range by the date and time and the duration
     * @param start The start date and time
     * @param durationMinutes The duration of the reservation
     * @return The list of reservations
     */
    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        try {
            return ReservationDaoImpl.getInstance().findReservationsWithinPeriod(start, durationMinutes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that is called when the client finalizes the reservation
     * Creates the reservation in the database
     * @param builder The builder of the reservation
     */
    @Override
    public void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException {
        try {
            Reservation res = ReservationDaoImpl.getInstance().create(builder);
            this.reservationSupport.firePropertyChange("reservation_created", null, res);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * A method that adds a property change listener to the registration
     * @param listener The listener to be added
     */
    public void addRegistrationPropertyChangeListener(
            RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
        this.registrationSupport.addPropertyChangeListener(listener);
    }

    /**
     * A method that adds a property change listener to the reservation
     * @param listener The listener to be added
     */
    public void addReservationPropertyChangeListener(
            RemotePropertyChangeListener<Serializable> listener) throws RemoteException {
        this.reservationSupport.addPropertyChangeListener(listener);
    }

    /**
     * A method that searches for the reservations by the phone number
     * @param phone The phone number of the client
     * @return The list of reservations
     */
    @Override
    public List<Reservation> onSearch(String phone) throws RemoteException {
        try {
            return ReservationDaoImpl.getInstance().readByPhoneNumber(phone);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that creates a feedback
     * @param content The content of the feedback
     * @param selectedType The type of the feedback
     * @param firstname The first name of the client
     * @param lastname The last name of the client
     * @return True if the feedback is created, false otherwise
     */
    @Override
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        try {
            return FeedbackDaoImplementation.getInstance().createFeedback(content, selectedType, firstname, lastname);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    /**
     * A method that creates a notification
     * @param notification The notification to be created
     */
    @Override
    public void createNotification(Notification notification) throws RemoteException{
        try {
            NotificationDaoImpl.getInstance().createNotification(notification);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    /**
     * A method that fetches the notifications
     * @return The list of notifications
     */
    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        try {
            return NotificationDaoImpl.getInstance().fetchNotifications();
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }

    /**
     * A method that marks the notification as read
     * @param notification The notification to be marked as read
     */
    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        try {
            NotificationDaoImpl.getInstance().markNotificationAsRead(notification);
        } catch (SQLException e) {
            throw new RemoteException();
        }
    }
}
