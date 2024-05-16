package via.dk.cueandbrew.view.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;

public class FinalizeReservationController
{
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
    this.viewModel = viewModel;
  }

  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onFinalize() {
    String firstname = this.firstnameField.getText();
    String lastname = this.lastnameField.getText();
    String phonenumber = this.phonenumberField.getText();
    String notes = this.notesField.getText();
    this.viewModel.onFinalize(firstname, lastname, phonenumber, notes);
  }
}
