package via.dk.cueandbrew.view.Reservation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.shared.Order;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

import java.util.List;

public class OrderController
{
  private OrderViewModel viewModel;
  private List<Drink> drinks;
 private  List<Drink> orders;
 @FXML
 private VBox menu;
 @FXML
  private VBox selections;
 private HBox doubles;

  public void init(OrderViewModel viewModel)
  {
    this.viewModel = viewModel;
    drinks=this.viewModel.getDrinks();
    orders=this.viewModel.getOrders();
    for (Drink drink:drinks)
    {
     int count=1;
      Button button= new Button();
      Label label=new Label();
      label.setText(count+"."+drink.getName()+" - "+drink.getPrice()+"dkk - "+drink.getQuantityOfDrink()+"ml ");
      count++;
      HBox box=new HBox(label,button);
      menu.getChildren().add(box);
      box.setStyle("-fx-border-color: black; " +
          "-fx-border-width: 1px; " +
          "-fx-border-style: solid;" +
          "-fx-border-radius: 5px;");
      button.setTranslateX(20);
      button.setText("+");
      button.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override public void handle(ActionEvent event)
        {
          for (Drink drink:orders)
          {
            int count=1;
            Button button1= new Button();
            Label label1=new Label();
            label1.setText(count+"."+drink.getName()+" - "+drink.getPrice()+"dkk - "+drink.getQuantityOfDrink()+"ml ");
            count++;
            HBox box2=new HBox(label1,button1);
            selections.getChildren().add(box2);
            box2.setStyle("-fx-border-color: black; " +
                "-fx-border-width: 1px; " +
                "-fx-border-style: solid;" +
                "-fx-border-radius: 5px;");
            button1.setTranslateX(20);
            button1.setText("-");
            button1.setOnAction(new EventHandler<ActionEvent>()
            {
              @Override public void handle(ActionEvent event)
              {
                selections.getChildren().remove(box2);
              }
            });
          }
        }
      });
    }

  }


  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onBackToReservations() {
    this.viewModel.onBackToReservations();
  }

  public void onSkip() {
    this.viewModel.onSkip();
  }

  public void onConfirm() {
    this.viewModel.onConfirm();
  }

}
