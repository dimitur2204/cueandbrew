package via.dk.cueandbrew.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import via.dk.cueandbrew.server.ServerInterface;
import via.dk.cueandbrew.shared.Feedback;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * A class that implements the CallbackClient interface and extends the UnicastRemoteObject class
 *
 * @author Darja Jefremova, Dimitar Nizamov, Marius Marcoci, Andreea Caisim
 */
public class CallbackClientImplementation extends UnicastRemoteObject implements
        RemotePropertyChangeListener<Serializable>, CallbackClient {
    private final ServerInterface serverInterface;
    private final PropertyChangeSupport support;

    /**
     * A constructor that creates a new CallbackClientImplementation object
     *
     * @param serverInterface the server interface
     * @throws RemoteException if the server interface is not available
     */
    public CallbackClientImplementation(ServerInterface serverInterface) throws RemoteException {
        super(0);
        this.serverInterface = serverInterface;
        this.serverInterface.addRegistrationPropertyChangeListener(this);
        this.serverInterface.addReservationPropertyChangeListener(this);
        this.serverInterface.addFeedbackPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * A method that is called for logging in
     *
     * @param login    the login of the user
     * @param password the password of the user
     * @param id       the id of the registratino
     * @throws RemoteException an error while logging in
     */
    @Override
    public void onLogin(String login, String password, UUID id)
            throws RemoteException {
        this.serverInterface.onLogin(login, password, id);
    }

    /**
     * A method that gets the reservations by date and time and duration
     *
     * @param start           the start date and time of the reservation
     * @param durationMinutes the duration of the reservation
     * @return a list of reservations
     * @throws RemoteException an error while fetching
     */
    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        return this.serverInterface.getReservationsByDateTimeAndDuration(start, durationMinutes);
    }

    /**
     * A method that is called for finalizing the reservation
     *
     * @param builder the reservation builder
     * @throws RemoteException an error while finalizing the reservation
     */
    @Override
    public void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException {
        this.serverInterface.onFinalizeReservation(builder);
    }

    /**
     * A method that adds a property change listener
     *
     * @param listener the listener
     */
    @Override
    public void addPropertyChange(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * A method that is called for searching a reservation by phone
     *
     * @param phone the phone number
     * @return a list of reservations
     * @throws RemoteException an error while searching
     */
    @Override
    public List<Reservation> onSearch(String phone) throws RemoteException {
        return this.serverInterface.onSearch(phone);
    }

    /**
     * A method that is called for creating a feedback
     *
     * @param content      the content of the feedback
     * @param selectedType the type of the feedback
     * @param firstname    the first name of the author of the feedback
     * @param lastname     the last name of the author of the feedback
     * @return true if the feedback is created, false if not
     * @throws RemoteException an error while creating the feedback
     */
    @Override
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        return this.serverInterface.createFeedback(content, selectedType, firstname, lastname);
    }

    /**
     * A method that fetches the notifications
     *
     * @return a list of notifications
     * @throws RemoteException an error while fetching the notifications
     */
    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        return this.serverInterface.fetchNotifications();
    }

    @Override public void addDrink(String name, double price, int quantity)
        throws RemoteException
    {
        this.serverInterface.addDrink(name, price, quantity);
    }

    /**
     * A method that marks a notification as read
     *
     * @param notification the notification that is to be marked as read
     * @throws RemoteException an error while marking the notification as read
     */
    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        this.serverInterface.markNotificationAsRead(notification);
    }

    /**
     * A method that creates a notification
     *
     * @param message the notification that is to be created
     * @throws RemoteException an error while creating the notification
     */
    @Override
    public void createNotification(Notification message) throws RemoteException {
        this.serverInterface.createNotification(message);
    }

    @Override public boolean cancelReservation(int id) throws RemoteException
    {
        return this.serverInterface.cancelReservation(id);
    }

    @Override public List<Feedback> fetchFeedbacks() throws RemoteException
    {
        return this.serverInterface.fetchFeedbacks();
    }

    @Override public boolean checkFeedback(int managerId, int feedbackId)
            throws RemoteException
    {
        return this.serverInterface.checkFeedback(managerId, feedbackId);
    }

    /**
     * A method that is called when a property is changed
     *
     * @param remotePropertyChangeEvent the remote property change event
     * @throws RemoteException an error while changing the property
     */
    @Override
    public void propertyChange(
            RemotePropertyChangeEvent<Serializable> remotePropertyChangeEvent)
            throws RemoteException {
        Platform.runLater(() -> {
            if (remotePropertyChangeEvent.getPropertyName().equals("login")) {
                Registration clientRegistration = (Registration) remotePropertyChangeEvent.getNewValue();
                Registration.getInstance().setManager_id(clientRegistration.getManager_id());
                Registration.getInstance().setLogin(clientRegistration.getLogin());
                Registration.getInstance().setId(clientRegistration.getId());
                this.support.firePropertyChange("login", null, clientRegistration);
            }
            else if (remotePropertyChangeEvent.getPropertyName().equals("reservation_created")) {
                this.support.firePropertyChange("reservation_created", null, remotePropertyChangeEvent.getNewValue());
            }
            else if (remotePropertyChangeEvent.getPropertyName().equals("created_feedback"))
            {
                this.support.firePropertyChange("created_feedback", null, remotePropertyChangeEvent.getNewValue());
            }
            else if (remotePropertyChangeEvent.getPropertyName().equals("check_feedback"))
            {
                this.support.firePropertyChange("check_feedback", null, remotePropertyChangeEvent.getNewValue());
            }
        });
    }
}
