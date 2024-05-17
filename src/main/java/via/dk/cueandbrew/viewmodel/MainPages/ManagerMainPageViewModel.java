package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.Table;
import via.dk.cueandbrew.view.ViewHandler;

<<<<<<< Updated upstream
import java.sql.Date;
import java.sql.Time;

public class ManagerMainPageViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private ObservableList<Notification> notifications;
=======
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class ManagerMainPageViewModel implements PropertyChangeListener {
    private PropertyChangeSupport support;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("welcome")) {
            this.welcomeLabel.setValue("Welcome " + evt.getNewValue().toString());
        }
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
    private final StringProperty welcomeLabel;
>>>>>>> Stashed changes

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
<<<<<<< Updated upstream
        this.notifications = FXCollections.observableArrayList();
        var table = new Table(2);
        var booking = new Booking(
                new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                new Time(System.currentTimeMillis())
        );
        booking.getTables().add(table);
        Reservation reservation = new Reservation.ReservationBuilder()
                .setClientFirstName("Lol")
                .setClientLastName("Lol")
                .setClientPhoneNumber("Lol")
                .setBooking(booking)
                .build();
        this.notifications.add(new Notification(reservation));
=======
    }

    public List<Notification> fetchNotifications() throws RemoteException {
        return this.model.fetchNotifications();
>>>>>>> Stashed changes
    }


    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    public void onExit() {
        this.viewHandler.openManagerLoginView();
    }

<<<<<<< Updated upstream
    public ObservableList<Notification> getNotifications() {
        return notifications;
=======
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
>>>>>>> Stashed changes
    }
}
