package via.dk.cueandbrew.model;

import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.List;

public interface Model
{
  void onLogin(String login, String password);
  void addPropertyChangeListener(PropertyChangeListener listener);
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes);
}
