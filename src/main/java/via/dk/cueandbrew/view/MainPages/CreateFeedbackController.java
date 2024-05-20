package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import via.dk.cueandbrew.viewmodel.MainPages.CreateFeedbackViewModel;
import java.rmi.RemoteException;

public class CreateFeedbackController
{
  @FXML private Button finalizeButton;
  @FXML private ComboBox<String> typeDropdown;
  @FXML private TextField firstname;
  @FXML private TextField lastname;
  @FXML private TextArea feedback;
  private CreateFeedbackViewModel viewModel;

 public void init(CreateFeedbackViewModel viewModel) {
   this.viewModel = viewModel;
   firstname.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   lastname.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   feedback.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   this.typeDropdown.getItems().addAll("Positive", "Negative");
   this.typeDropdown.setValue("Positive");
 }

  private void checkInput() {
    boolean isFirstNameEmpty = firstname.getText().isEmpty();
    boolean isLastNameEmpty = lastname.getText().isEmpty();
    boolean isFeedbackEmpty = feedback.getText().isEmpty();

    finalizeButton.setDisable(isFirstNameEmpty || isLastNameEmpty || isFeedbackEmpty);
  }

 public void onCancel() {
   this.viewModel.onCancel();
 }

  public void onFinalize() throws RemoteException
  {
    showPopup(this.viewModel.onFinalize(this.feedback.textProperty().get(),
        this.typeDropdown.getSelectionModel().getSelectedItem(),
        this.firstname.getText(), this.lastname.getText()) != null);
  }

  private void showPopup(boolean confirmation) {
    Alert alert;
    if (confirmation) {
      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Success");
      alert.setHeaderText("Feedback Submitted");
      alert.setContentText("Your feedback has been successfully submitted.");
    } else {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failure");
      alert.setHeaderText("Submission Failed");
      alert.setContentText("There was an error submitting your feedback. Please try again.");
    }

    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okButton.setOnAction(event -> {
      alert.close();
      this.viewModel.onCancel();
    });

    alert.showAndWait();
  }
}