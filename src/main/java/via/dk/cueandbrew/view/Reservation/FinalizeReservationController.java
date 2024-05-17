package via.dk.cueandbrew.view.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField phoneNumberField;
    @FXML
    private TextArea notesField;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    private FinalizeReservationViewModel viewModel;

    public void init(FinalizeReservationViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
        this.finalizeBtn.setDisable(true);
        attachValidationListener(firstnameField);
        attachValidationListener(lastnameField);
        attachValidationListener(phoneNumberField);
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }

  public void onFinalize() {
    String firstname = this.firstnameField.getText();
    String lastname = this.lastnameField.getText();
    String phoneNumber = this.phoneNumberField.getText();
    String notes = this.notesField.getText();
    this.viewModel.onFinalize(firstname, lastname, phoneNumber, notes);
  }
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
