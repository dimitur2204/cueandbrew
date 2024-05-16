package via.dk.cueandbrew.model;

import javafx.application.Platform;
import via.dk.cueandbrew.client.CallbackClient;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public class ModelManager implements Model, PropertyChangeListener
{
  private final CallbackClient client;
  private final PropertyChangeSupport support;
  private Registration registration;
  private final Reservation.ReservationBuilder reservationBuilder;

  public ModelManager(CallbackClient client) {
    this.client = client;
    this.client.addPropertyChange(this);
    this.registration = new Registration();
    this.reservationBuilder = new Reservation.ReservationBuilder();
    this.support = new PropertyChangeSupport(this);
  }

  public Registration getRegistration()
  {
    return registration;
  }

  public Reservation.ReservationBuilder getReservationBuilder() {
    return reservationBuilder;
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

  @Override
  public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
    return this.client.getReservationsByDateTimeAndDuration(start, durationMinutes);
  }

  @Override
  public void onFinalizeReservation() throws RemoteException {
    this.client.onFinalizeReservation(this.reservationBuilder);
  }

  @Override public List<Reservation> onSearch(String phone) throws RemoteException
  {
    return this.client.onSearch(phone);
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
