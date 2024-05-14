package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.shared.Order;
import via.dk.cueandbrew.view.ViewHandler;

public class OrderViewModel
{
  private Model model;
  private ViewHandler viewHandler;
  private ObservableList<Drink> drinks;
  private ObservableList<Drink> orders ;

  public OrderViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    drinks= FXCollections.observableArrayList();
    orders= FXCollections.observableArrayList();
    Drink drink=new Drink("Cuba Libre",80.0,400);
    drinks.add(drink);
    orders.add(drink);
  }

  public void onCancel()
  {
    this.viewHandler.openManagerMainPage();
  }

  public void onBackToReservations()
  {
    this.viewHandler.openCreateReservationView();
  }
  public ObservableList<Drink> getDrinks()
  {
    return drinks;
  }
  public ObservableList<Drink> getOrders()
  {
    return orders;
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
  public void onSkip()
  {
    this.viewHandler.openFinalizeReservationView();
  }

  public void onConfirm()
  {
    this.viewHandler.openFinalizeReservationView();
  }
}
