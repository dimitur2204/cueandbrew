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

public class CallbackClientImplementation extends UnicastRemoteObject implements
        RemotePropertyChangeListener<Serializable>, CallbackClient {
    private final ServerInterface serverInterface;
    private final PropertyChangeSupport support;

    public CallbackClientImplementation(ServerInterface serverInterface) throws RemoteException {
        super(0);
        this.serverInterface = serverInterface;
        this.serverInterface.addRegistrationPropertyChangeListener(this);
        this.serverInterface.addReservationPropertyChangeListener(this);
        this.serverInterface.addFeedbackPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void onLogin(String login, String password, UUID id)
            throws RemoteException {
        this.serverInterface.onLogin(login, password, id);
    }

    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        return this.serverInterface.getReservationsByDateTimeAndDuration(start, durationMinutes);
    }

    @Override
    public void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException {
        this.serverInterface.onFinalizeReservation(builder);
    }

    @Override
    public void addPropertyChange(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    @Override
    public List<Reservation> onSearch(String phone) throws RemoteException {
        return this.serverInterface.onSearch(phone);
    }

    @Override
    public Feedback createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        return this.serverInterface.createFeedback(content, selectedType, firstname, lastname);
    }

    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        return this.serverInterface.fetchNotifications();
    }

    @Override public void addDrink(String name, double price, int quantity)
        throws RemoteException
    {
        this.serverInterface.addDrink(name, price, quantity);
    }

    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        this.serverInterface.markNotificationAsRead(notification);
    }

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
