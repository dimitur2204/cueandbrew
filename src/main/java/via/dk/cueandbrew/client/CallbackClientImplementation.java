package via.dk.cueandbrew.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import via.dk.cueandbrew.databse.dao.ReservationDaoImpl;
import via.dk.cueandbrew.server.ServerInterface;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

public class CallbackClientImplementation extends UnicastRemoteObject implements
        RemotePropertyChangeListener<Registration>, CallbackClient {
    private final ServerInterface serverInterface;
    private final PropertyChangeSupport support;

    public CallbackClientImplementation(ServerInterface serverInterface) throws RemoteException {
        super(0);
        this.serverInterface = serverInterface;
        this.serverInterface.addPropertyChangeListener(this);
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
    public void propertyChange(
            RemotePropertyChangeEvent<Registration> remotePropertyChangeEvent)
            throws RemoteException {
        Platform.runLater(() -> {
            if (remotePropertyChangeEvent.getPropertyName().equals("login")) {
                this.support.firePropertyChange("login", null, remotePropertyChangeEvent.getNewValue());
            }
        });
    }
}
