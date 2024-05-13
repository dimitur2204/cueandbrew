package via.dk.cueandbrew.view.Start;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ManagerLoginController implements PropertyChangeListener
{
  private ManagerLoginViewModel viewModel;

  @FXML Label errorLabel;
  @FXML Button loginButton;
  @FXML TextField usernameField;
  @FXML TextField passwordField;

  public void init(ManagerLoginViewModel viewModel) {
    this.viewModel = viewModel;
    this.usernameField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButtonState());
    this.passwordField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButtonState());
    this.viewModel.bindErrorLabel(errorLabel.textProperty());
    this.viewModel.addPropertyChangeListener(this);
  }

  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onLogin() {
    this.viewModel.onLogin(this.usernameField.getText(), this.passwordField.getText());
  }

  private void updateLoginButtonState() {
    boolean isUsernameEmpty = usernameField.getText().isEmpty();
    boolean isPasswordEmpty = passwordField.getText().isEmpty();
    loginButton.setDisable(isUsernameEmpty || isPasswordEmpty);
  }

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
