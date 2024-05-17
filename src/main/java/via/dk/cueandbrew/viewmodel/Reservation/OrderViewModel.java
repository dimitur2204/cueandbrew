package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.shared.Order;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private List<Drink> drinks;
    private ObservableList<Drink> orderedDrinks;

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

    /*<HBox fx:id="id" alignment="CENTER" prefHeight="30.0" prefWidth="290.0" style="-fx-border-color: black; -fx-border-radius: 10; -fx: 0 0 0 0;">
                         <children>
                            <HBox alignment="CENTER_LEFT" prefHeight="100.0" prefWidth="200.0">
                               <children>
                                  <Label text="1. Mojito - 150ml - 70dkk" />
                               </children>
                               <opaqueInsets>
                                  <Insets />
                               </opaqueInsets>
                            </HBox>
                            <HBox alignment="CENTER_RIGHT" prefHeight="100.0" prefWidth="200.0">
                               <children>
                                  <Button mnemonicParsing="false" prefHeight="25.0" prefWidth="26.0" style="-fx-background-radius: 100;" text="-" />
                               </children>
                               <opaqueInsets>
                                  <Insets bottom="5.0" />
                               </opaqueInsets>
                            </HBox>
                         </children>
                      </HBox>
                      */
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
