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

    }
    @FXML
    public void onAddDrink(){
        viewModel.onAddDrink(name.getText(),Double.parseDouble(price.getText()),Integer.parseInt(
                quantity.getText()));

    }
    @FXML
    public void onCancel(){
        viewModel.onCancel();
    }
}