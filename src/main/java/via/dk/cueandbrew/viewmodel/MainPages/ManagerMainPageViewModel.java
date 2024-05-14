package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.Table;
import via.dk.cueandbrew.view.ViewHandler;

import java.awt.print.Book;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class ManagerMainPageViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private ObservableList<Notification> notifications;

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.notifications = FXCollections.observableArrayList(new ArrayList<>());
        var table = new Table(2);
        var bookings = new ArrayList<Booking>();
        var booking = new Booking(
                new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                new Time(System.currentTimeMillis())
        );
        booking.getTables().add(table);
        bookings.add(booking);
        Reservation reservation = new Reservation.ReservationBuilder("Test", "Test2", "1234")
                .setBooking(bookings)
                .build();
        this.notifications.add(new Notification(reservation));
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    public void onExit() {
        this.viewHandler.openManagerLoginView();
    }
    @FXML
    public void onAddADrink(){
        this.viewHandler.openAddDrinkManager();
    }
}
