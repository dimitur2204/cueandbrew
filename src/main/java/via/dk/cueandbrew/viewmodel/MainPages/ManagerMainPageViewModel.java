package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.*;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;

public class ManagerMainPageViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ViewHandler viewHandler;
    private final ObservableList<Notification> notifications;
    private final StringProperty welcomeLabel;

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.viewHandler = viewHandler;
        this.notifications = FXCollections.observableArrayList();
        this.welcomeLabel = new SimpleStringProperty();
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
    }

    public void bindWelcomeLabel(StringProperty property) {
        property.bind(welcomeLabel);
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
        Registration temp = Registration.getInstance();
        temp.setManager_id(-1);
        temp.setLogin("");
        this.welcomeLabel.setValue("");
        this.viewHandler.openManagerLoginView();
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("welcome")) {
            this.welcomeLabel.setValue("Welcome " + evt.getNewValue().toString());
        }
    }
}
