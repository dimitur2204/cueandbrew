package via.dk.cueandbrew.view.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;

public class FinalizeReservationController {
    @FXML
    private Button finalizeBtn;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private TextArea notesField;
    private FinalizeReservationViewModel viewModel;

    public void init(FinalizeReservationViewModel viewModel) {
        this.finalizeBtn.setDisable(true);
        attachValidationListener(firstnameField);
        attachValidationListener(lastnameField);
        attachValidationListener(phonenumberField);
        this.viewModel = viewModel;
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }

    private void attachValidationListener(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || phonenumberField.getText().isEmpty()) {
                finalizeBtn.setDisable(true);
            } else {
                finalizeBtn.setDisable(false);
            }
        });

    }

    public void onFinalize() {
        String firstname = this.firstnameField.getText();
        String lastname = this.lastnameField.getText();
        String phonenumber = this.phonenumberField.getText();
        String notes = this.notesField.getText();
        this.viewModel.onFinalize(firstname, lastname, phonenumber, notes);
    }
}
