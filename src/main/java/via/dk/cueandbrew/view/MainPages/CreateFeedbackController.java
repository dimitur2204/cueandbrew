package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import via.dk.cueandbrew.viewmodel.MainPages.CreateFeedbackViewModel;
import java.rmi.RemoteException;

/**
 * A class that is responsible for the CreateFeedbackController
 * @author Marius Marcoci

 */
public class CreateFeedbackController
{
  @FXML private Button finalizeButton;
  @FXML private ComboBox<String> typeDropdown;
  @FXML private TextField firstname;
  @FXML private TextField lastname;
  @FXML private TextArea feedback;
  private CreateFeedbackViewModel viewModel;

    /**
     * A method that initializes the CreateFeedbackViewModel
     * @param viewModel The CreateFeedbackViewModel
     */
 public void init(CreateFeedbackViewModel viewModel) {
   this.viewModel = viewModel;
   firstname.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   lastname.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   feedback.textProperty().addListener((observable, oldValue, newValue) -> checkInput());
   this.typeDropdown.getItems().addAll("Positive", "Negative");
   this.typeDropdown.setValue("Positive");
 }

 /** Validates the input fields and enables the finalize button if all fields are filled out */
  private void checkInput() {
    boolean isFirstNameEmpty = firstname.getText().isEmpty();
    boolean isLastNameEmpty = lastname.getText().isEmpty();
    boolean isFeedbackEmpty = feedback.getText().isEmpty();

    finalizeButton.setDisable(isFirstNameEmpty || isLastNameEmpty || isFeedbackEmpty);
  }

    /**
     * A method that calls the onCancel method from the viewModel
     */
 public void onCancel() {
   this.viewModel.onCancel();
 }

    /**
     * A method that calls the onFinalize method from the viewModel and passes the feedback, type, firstname and lastname
     * @throws RemoteException
     */
  public void onFinalize() throws RemoteException
  {
    boolean confirmation = this.viewModel.onFinalize(this.feedback.textProperty().get(), this.typeDropdown.getSelectionModel().getSelectedItem(), this.firstname.getText(), this.lastname.getText());
    showPopup(confirmation);
  }

    /**
     * A method that shows a popup
     * @param confirmation A boolean that is true if the feedback was submitted successfully
     */
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