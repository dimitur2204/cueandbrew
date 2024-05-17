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

public class ManagerLoginViewModel implements PropertyChangeListener
{
  private final Model model;
  private final ViewHandler viewHandler;
  private final StringProperty errorLabel;
  private final PropertyChangeSupport support;
  private UUID id;

  public ManagerLoginViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.model.addPropertyChangeListener(this);
    this.errorLabel = new SimpleStringProperty();
    this.support = new PropertyChangeSupport(this);
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  public void onCancel()
  {
    this.viewHandler.openStartView();
  }

  public void bindErrorLabel(StringProperty property) {
    property.bind(errorLabel);
  }

  public void onLogin(String login, String password)
  {
    this.model.onLogin(login, password, id);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.support.addPropertyChangeListener(listener);
  }

  public void  clearErrorLabel() {
    this.errorLabel.setValue("");
  }

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
