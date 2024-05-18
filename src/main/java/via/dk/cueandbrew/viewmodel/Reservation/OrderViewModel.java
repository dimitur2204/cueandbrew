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

/**
 * A class that is responsible for the OrderViewModel
 * @author Andreea Caisim
 */
public class OrderViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private List<Drink> drinks;
    private ObservableList<Drink> orderedDrinks;

    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
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

    /**
     * A method that updates the date and time
     * @param date The date
     * @param time The time
     */
    public void updateDateTime(Label date, Label time) {
    this.model.updateDateTime(date, time);
    }

    /**
     * A method that starts the date and time updater
     * @param date The date
     * @param time The time
     */
    public void startDateTimeUpdater(Label date, Label time) {
    this.model.startDateTimeUpdater(date, time);
    }

    /**
     * A method that gets the booking date
     * @return The booking date
     */
    public Date getBookingDate(){
        var res = this.model.getReservationBuilder().build();
        return res.getBooking().getDate();
    }

    /**
     * A method that gets the booking hour
     * @return The booking hour
     */
   public int getBookingHour(){
        var res = this.model.getReservationBuilder().build();
       Time startTime = res.getBooking().getStartTime();
       return startTime.getHours();
   }

   /**
    * A method that gets the booking minute
    * @return The booking minute
    */
   public int getBookingMinute() {
       var res = this.model.getReservationBuilder().build();
       Time startTime = res.getBooking().getStartTime();
       return startTime.getMinutes();
   }

   /** Cancel the order */
    public void onCancel() {
        this.viewHandler.openManagerMainPage();
    }

    /**
     * A method that gets the expected time
     * @return The expected time
     */
    public void onBackToReservations() {
        this.viewHandler.openCreateReservationView();
    }

    /**
     * A method that gets the drinks
     * @return The drinks
     */
    public List<Drink> getDrinks() {
        return drinks;
    }


    /**
     * A method that adds a drink
     * @param drink The drink
     */
    public void addDrink(Drink drink) {
        orderedDrinks.add(drink);
    }

    /**
     * A method that removes a drink
     */
    public ObservableList<Drink> getOrderedDrinks() {
        return orderedDrinks;
    }

    /**
     * A method that removes a drink
     */
    public void onSkip() {
        this.viewHandler.openFinalizeReservationView();
    }

    /**
     * A method that confirms the order
     * @param expected The expected time
     */
    public void onConfirm(Timestamp expected) {
        Order newOrder = new Order(expected);
        for (Drink drink : orderedDrinks) {
            newOrder.getDrinks().add(drink);
        }
        model.getReservationBuilder().setOrder(newOrder);
        this.viewHandler.openFinalizeReservationView();
    }
}
