package via.dk.cueandbrew.view.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.view.Toast;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;

/**
 * A class that is responsible for the FinalizeReservationController
 * @author Andreea Caisim, Darja Jefremova
 */
public class FinalizeReservationController {
    @FXML
    private Button finalizeBtn;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextArea notesField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    private FinalizeReservationViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * A method that initializes the FinalizeReservationViewModel
     * @param viewModel The FinalizeReservationViewModel
     * @param viewHandler The viewHandler
     */
    public void init(FinalizeReservationViewModel viewModel, ViewHandler viewHandler) {
        this.viewModel = viewModel;
        this.viewHandler = viewHandler;
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
        this.finalizeBtn.setDisable(true);
        attachValidationListener(firstnameField);
        attachValidationListener(lastnameField);
        attachValidationListener(phoneNumberField);
    }

    /**
     * A method that calls the onCancel method from the viewModel
     */
    public void onCancel() {
        this.viewModel.onCancel();
    }

    /**
     * A method that calls the onFinalize method from the viewModel and passses the firstname, lastname, phoneNumber and notes
     */
    public void onFinalize() {
        String firstname = this.firstnameField.getText();
        String lastname = this.lastnameField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String notes = this.notesField.getText();
        this.viewModel.onFinalize(firstname, lastname, phoneNumber, notes);
        Toast.makeText(this.viewHandler.getStage(), "Reservation created!");
    }

    /**
     * A method that attaches a validation listener to a text field to check for empty fields
     * @param field The text field
     */
    private void attachValidationListener(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
                finalizeBtn.setDisable(true);
            } else {
                finalizeBtn.setDisable(false);
            }
        });

    }
}
