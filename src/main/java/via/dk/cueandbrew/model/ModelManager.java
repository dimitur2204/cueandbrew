package via.dk.cueandbrew.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;
import via.dk.cueandbrew.client.CallbackClient;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModelManager implements Model, PropertyChangeListener
{
  private final CallbackClient client;
  private final PropertyChangeSupport support;
  private final Reservation.ReservationBuilder reservationBuilder;

  public ModelManager(CallbackClient client) {
    this.client = client;
    this.client.addPropertyChange(this);
    this.reservationBuilder = new Reservation.ReservationBuilder();
    this.support = new PropertyChangeSupport(this);
  }

  public Reservation.ReservationBuilder getReservationBuilder() {
    return reservationBuilder;
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

  @Override public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException
  {
    return this.client.createFeedback(content, selectedType, firstname, lastname);
  }

  @Override public void startDateTimeUpdater(Label date, Label time)
  {
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(1), event -> updateDateTime(date, time))
    );
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  @Override public void updateDateTime(Label date, Label time)
  {
    LocalDateTime now = LocalDateTime.now();

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    String formattedDate = now.format(dateFormatter);
    String formattedTime = now.format(timeFormatter);

    Platform.runLater(() -> {
      date.setText(formattedDate);
      time.setText(formattedTime);
    });
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      if (evt.getPropertyName().equals("login")) {
        Registration temp = (Registration) evt.getNewValue();
        if (temp != null) {
          //there is a registration
          this.support.firePropertyChange("login", null, "true");
        }
        else {
          //there isn't a registration
          this.support.firePropertyChange("login", null, "false");
        }
      }
    });
  }
}
