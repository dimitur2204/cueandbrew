package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.shared.Order;
import via.dk.cueandbrew.view.ViewHandler;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel {
    private final Model model;
    private final ViewHandler viewHandler;
    private final List<Drink> drinks;
    private final ObservableList<Drink> orderedDrinks;

    public OrderViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.drinks = new ArrayList<>();
        Drink drink = new Drink(1, "Cuba Libre", 80.0, 400);
        Drink drink2 = new Drink(2, "Mojito", 90.0, 500);
        drinks.add(drink);
        drinks.add(drink2);
        this.orderedDrinks = FXCollections.observableArrayList();
    }

    public void updateDateTime(Label date, Label time) {
    this.model.updateDateTime(date, time);
    }

    public void startDateTimeUpdater(Label date, Label time) {
    this.model.startDateTimeUpdater(date, time);
    }

    public Date getBookingDate(){
        var res = this.model.getReservationBuilder().build();
        return res.getBooking().getDate();
    }

   public int getBookingHour(){
        var res = this.model.getReservationBuilder().build();
       Time startTime = res.getBooking().getStartTime();
       return startTime.getHours();
   }

   public int getBookingMinute() {
       var res = this.model.getReservationBuilder().build();
       Time startTime = res.getBooking().getStartTime();
       return startTime.getMinutes();
   }

    public void onCancel() {
        this.viewHandler.openManagerMainPage();
    }

    public void onBackToReservations() {
        this.viewHandler.openCreateReservationView();
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public ObservableList<Drink> getOrderedDrinks() {
        return orderedDrinks;
    }

    public void onSkip() {
        this.viewHandler.openFinalizeReservationView();
    }

    public void onConfirm(Timestamp expected) {
        Order newOrder = new Order(expected);
        for (Drink drink : orderedDrinks) {
            newOrder.getDrinks().add(drink);
        }
        model.getReservationBuilder().setOrder(newOrder);
        this.viewHandler.openFinalizeReservationView();
    }
}
