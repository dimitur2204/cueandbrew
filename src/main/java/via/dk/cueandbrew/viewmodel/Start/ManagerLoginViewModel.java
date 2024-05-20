package via.dk.cueandbrew.viewmodel.Start;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

/**
 * A class that is responsible for the ManagerLoginViewModel
 * @author Marius Marcoci, Darja Jefromova
 */
public class ManagerLoginViewModel implements PropertyChangeListener
{
  private final Model model;
  private final ViewHandler viewHandler;
  private final StringProperty errorLabel;
  private final PropertyChangeSupport support;
  private UUID id;

  /**
   * A constructor that sets the model and the viewHandler
   * @param model The model
   * @param viewHandler The viewHandler
   */
  public ManagerLoginViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.model.addPropertyChangeListener(this);
    this.errorLabel = new SimpleStringProperty();
    this.support = new PropertyChangeSupport(this);
  }

    /**
     * A method that sets the id
     * @param id The id
     */
  public void setId(UUID id)
  {
    this.id = id;
  }

    /**
     * A method that opens the start view
     */
  public void onCancel()
  {
    this.viewHandler.openStartView();
  }

  /**
   * A method that binds the error label
   * @param property The property
   */
  public void bindErrorLabel(StringProperty property) {
    property.bind(errorLabel);
  }

  /**
   * A method that sends the login and the password to the model
   * @param login The login
   * @param password The password
   */
  public void onLogin(String login, String password)
  {
    this.model.onLogin(login, password, id);
  }

  /**
   * A method that adds a property change listener
   * @param listener The listener
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.support.addPropertyChangeListener(listener);
  }

  /**
   * A method that clears the error label
   */
  public void  clearErrorLabel() {
    this.errorLabel.setValue("");
  }

  /**
   * A method that listens for property changes
   * @param evt The event
   */
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("login")) {
        if (evt.getNewValue().equals("true")) {
          if (this.id.equals(Registration.getInstance().getId())) {
            this.viewHandler.openManagerMainPage();
            clearErrorLabel();
          }
        }
        else if (evt.getNewValue().equals("false"))
        {
          if (Registration.getInstance().getId().equals(this.id)) {
            this.errorLabel.set("Wrong username or password");
          }
        }
        this.support.firePropertyChange("clear", null, "change");
      }
    });
  }
}
