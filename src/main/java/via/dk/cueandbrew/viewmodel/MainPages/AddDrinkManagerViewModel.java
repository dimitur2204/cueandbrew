package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

public class AddDrinkManagerViewModel
{
  private Model model;
  private ViewHandler viewHandler;
  private OrderViewModel orderViewModel;
  private StringProperty name;
  private DoubleProperty price;
  private IntegerProperty quantity;
  public AddDrinkManagerViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.orderViewModel=new OrderViewModel(model,viewHandler);
    price=new SimpleDoubleProperty();
    name=new SimpleStringProperty();
    quantity=new SimpleIntegerProperty();
  }

  public void onAddDrink(){
   viewHandler.openOrder();
   Drink drink=new Drink(name.getValue(), price.get(), quantity.get());
   orderViewModel.getDrinks().add(drink);
  }

  public void onCancel(){
    viewHandler.openManagerMainPage();
  }

  public void setName(String name)
  {
    this.name.set(name);
  }

  public void setPrice(double price)
  {
    this.price.set(price);
  }

  public void setQuantity(int quantity)
  {
    this.quantity.set(quantity);
  }
}
