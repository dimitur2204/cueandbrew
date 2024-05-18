package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.MainPages.AddDrinkManagerViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;


/**
 * A class that is responsible for the AddDrinkManagerController
 *
 * @author Andreea Caisim
 */
public class AddDrinkManagerController {
    private AddDrinkManagerViewModel viewModel;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;

    /**
     * A method that initializes the AddDrinkManagerViewModel
     *
     * @param viewModel The AddDrinkManagerViewModel
     */
    public void init(AddDrinkManagerViewModel viewModel) {
        this.viewModel = viewModel;

    }

    /**
     * A method that calls the onAddDrink method from the viewModel and passes the name, price and quantity
     */
    @FXML
    public void onAddDrink() {
        viewModel.onAddDrink(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(
                quantity.getText()));

    }

    /**
     * A method that calls the onCancel method from the viewModel
     */
    @FXML
    public void onCancel() {
        viewModel.onCancel();
    }
}