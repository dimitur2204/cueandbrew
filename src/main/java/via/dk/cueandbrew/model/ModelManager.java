package via.dk.cueandbrew.model;

import javafx.application.Platform;
import via.dk.cueandbrew.client.CallbackClient;
import via.dk.cueandbrew.shared.Registration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class ModelManager implements Model, PropertyChangeListener
{
  private final CallbackClient client;
  private final PropertyChangeSupport support;
  private Registration registration;

  public ModelManager(CallbackClient client) {
    this.client = client;
    this.client.addPropertyChange(this);
    this.registration = new Registration();
    this.support = new PropertyChangeSupport(this);
  }

  public Registration getRegistration()
  {
    return registration;
  }

  public void setRegistration(Registration registration)
  {
    this.registration = registration;
  }

  @Override public void onLogin(String login, String password)
  {
    try
    {
      this.client.onLogin(login, password);
    }
    catch (RemoteException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    this.support.addPropertyChangeListener(listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("login")) {
        Registration temp = (Registration) evt.getNewValue();
        if (temp != null) {
          setRegistration(temp);
          this.support.firePropertyChange("login", null, "true");
        }
        else {
          this.support.firePropertyChange("login", null, "false");
        }
      }
    });
  }
}
