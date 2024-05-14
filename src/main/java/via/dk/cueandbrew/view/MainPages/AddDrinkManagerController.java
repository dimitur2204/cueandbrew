package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.MainPages.AddDrinkManagerViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;

public class AddDrinkManagerController
{
  private AddDrinkManagerViewModel viewModel;
  @FXML
  private TextField name;
  @FXML
  private TextField price;
  @FXML
  private TextField quantity;
  public void init(AddDrinkManagerViewModel viewModel)
  {
    this.viewModel=viewModel;
    viewModel.setName(name.getText());
    System.out.println(name.getText());
  }
  @FXML
  public void onAddDrink(){
    viewModel.onAddDrink();
  }
  @FXML
  public void onCancel(){
    viewModel.onCancel();
  }
}
