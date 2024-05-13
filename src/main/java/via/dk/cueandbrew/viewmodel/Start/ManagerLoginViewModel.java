package via.dk.cueandbrew.viewmodel.Start;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ManagerLoginViewModel implements PropertyChangeListener
{
  private final Model model;
  private final ViewHandler viewHandler;
  private final StringProperty errorLabel;
  private final PropertyChangeSupport support;

  public ManagerLoginViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.model.addPropertyChangeListener(this);
    this.errorLabel = new SimpleStringProperty();
    this.support = new PropertyChangeSupport(this);
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
    this.model.onLogin(login, password);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    this.support.addPropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("login")) {
        if (evt.getNewValue().equals("true")) {
          this.viewHandler.openManagerMainPage();
          this.errorLabel.set("");
        }
        else if (evt.getNewValue().equals("false"))
        {
          this.errorLabel.set("Wrong username or password");
        }
        this.support.firePropertyChange("clear", null, "change");
      }
    });
  }
}
