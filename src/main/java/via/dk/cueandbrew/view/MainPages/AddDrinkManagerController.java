package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.MainPages.AddDrinkManagerViewModel;

import static javafx.scene.input.KeyEvent.KEY_TYPED;

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
    @FXML
    private Button finalizeButton;

    /**
     * A method that initializes the AddDrinkManagerViewModel
     *
     * @param viewModel The AddDrinkManagerViewModel
     */
    public void init(AddDrinkManagerViewModel viewModel) {
        this.viewModel = viewModel;
        name.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
        price.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
        quantity.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
        addTextFieldNumericOnlyListener(price);
        addTextFieldNumericOnlyListener(quantity);
    }

    private void addTextFieldNumericOnlyListener(TextField textField) {
        textField.addEventFilter(KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }

    private void checkInput() {
        boolean isFirstNameEmpty = name.getText().isEmpty();
        boolean isLastNameEmpty = price.getText().isEmpty();
        boolean isFeedbackEmpty = quantity.getText().isEmpty();

        finalizeButton.setDisable(isFirstNameEmpty || isLastNameEmpty || isFeedbackEmpty);
    }

    /**
     * A method that calls the onAddDrink method from the viewModel and passes the name, price and quantity
     */
    @FXML
    public void onAddDrink() {
        showPopup(viewModel.onAddDrink(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText())));
    }

    private void showPopup(boolean confirmation) {
        Alert alert;
        if (confirmation) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Drink added to the overall menu");
            alert.setContentText("Your drink was successfully added to the menu");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setHeaderText("Could not add the drink to the menu");
            alert.setContentText("Could not add the drink to the menu");
        }

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> {
            alert.close();
            if (confirmation) {
                this.viewModel.onCancel();
            }
        });

        alert.showAndWait();
    }

    /**
     * A method that calls the onCancel method from the viewModel
     */
    @FXML
    public void onCancel() {
        viewModel.onCancel();
    }
}