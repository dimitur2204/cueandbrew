package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.Table;
import via.dk.cueandbrew.view.ViewHandler;

import java.sql.Date;
import java.sql.Time;

public class ManagerMainPageViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private ObservableList<Notification> notifications;

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
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
    }


    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    public void onExit() {
        this.viewHandler.openManagerLoginView();
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }
}
