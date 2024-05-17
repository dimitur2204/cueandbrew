package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class ManagerMainPageViewModel implements PropertyChangeListener {
   private PropertyChangeSupport support;
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("reservation_created")) {
            Reservation reservation = (Reservation) evt.getNewValue();
            Notification notification = new Notification(reservation);
            try {
                this.model.createNotification(notification);
                this.support.firePropertyChange("notification_created", null, notification);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final Model model;
    private final ViewHandler viewHandler;

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.support = new PropertyChangeSupport(this);
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.viewHandler = viewHandler;
    }
    public List<Notification> fetchNotifications() throws RemoteException {
        return this.model.fetchNotifications();
    }

    public void updateDateTime(Label date, Label time) {
        this.model.updateDateTime(date, time);
    }

    public void startDateTimeUpdater(Label date, Label time) {
        this.model.startDateTimeUpdater(date, time);
    }

    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    public void onExit() {
        this.viewHandler.openManagerLoginView();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
