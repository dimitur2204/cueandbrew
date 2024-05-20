package via.dk.cueandbrew.view.Start;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A class that is responsible for the ManagerLoginController
 * @author Marius Marcoci
 */
public class ManagerLoginController implements PropertyChangeListener
{
  private ManagerLoginViewModel viewModel;

  @FXML Label errorLabel;
  @FXML Button loginButton;
  @FXML TextField usernameField;
  @FXML TextField passwordField;

    /**
     * A method that initializes the ManagerLoginViewModel
     * @param viewModel The ManagerLoginViewModel
     */
  public void init(ManagerLoginViewModel viewModel) {
    this.viewModel = viewModel;
    this.usernameField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButtonState());
    this.passwordField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButtonState());
    this.viewModel.bindErrorLabel(errorLabel.textProperty());
    this.viewModel.addPropertyChangeListener(this);
  }

    /**
     * A method that opens the start view
     */
  public void onCancel() {
    this.viewModel.onCancel();
  }

    /**
     * A method that logs in
     */
  public void onLogin() {
    this.viewModel.onLogin(this.usernameField.getText(), this.passwordField.getText());
  }

  /**
   * A method that updates the login button state
   */
  private void updateLoginButtonState() {
    boolean isUsernameEmpty = usernameField.getText().isEmpty();
    boolean isPasswordEmpty = passwordField.getText().isEmpty();
    loginButton.setDisable(isUsernameEmpty || isPasswordEmpty);
  }

  /** A method that listens for property changes */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("clear")) {
        if (evt.getNewValue().equals("change")) {
          this.usernameField.clear();
          this.passwordField.clear();
        }
      }
    });
  }
}
