package via.dk.cueandbrew.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import via.dk.cueandbrew.server.ServerInterface;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

public class CallbackClientImplementation extends UnicastRemoteObject implements
        RemotePropertyChangeListener<Serializable>, CallbackClient {
    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        return this.serverInterface.fetchNotifications();
    }

    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        this.serverInterface.markNotificationAsRead(notification);
    }

    @Override
    public void createNotification(Notification message) throws RemoteException {
        this.serverInterface.createNotification(message);
    }

    private final ServerInterface serverInterface;
    private final PropertyChangeSupport support;

    public CallbackClientImplementation(ServerInterface serverInterface) throws RemoteException {
        super(0);
        this.serverInterface = serverInterface;
        this.serverInterface.addRegistrationPropertyChangeListener(this);
        this.serverInterface.addReservationPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void onLogin(String login, String password)
            throws RemoteException {
        this.serverInterface.onLogin(login, password);
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
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        return this.serverInterface.createFeedback(content, selectedType, firstname, lastname);
    }

    @Override
    public void propertyChange(
            RemotePropertyChangeEvent<Serializable> remotePropertyChangeEvent)
            throws RemoteException {
        Platform.runLater(() -> {
            if (remotePropertyChangeEvent.getPropertyName().equals("login")) {
                Registration clientRegistration = (Registration) remotePropertyChangeEvent.getNewValue();
                if (clientRegistration != null) {
                    //there is a registration
                    Registration.getInstance().setManager_id(clientRegistration.getManager_id());
                    Registration.getInstance().setLogin(clientRegistration.getLogin());
                    this.support.firePropertyChange("login", null, clientRegistration);
                } else {
                    //there isn't a registration
                    this.support.firePropertyChange("login", Registration.getInstance(), null);
                }
            }
            if (remotePropertyChangeEvent.getPropertyName().equals("reservation_created")) {
                this.support.firePropertyChange("reservation_created", null, remotePropertyChangeEvent.getNewValue());
            }
        });
    }
}
