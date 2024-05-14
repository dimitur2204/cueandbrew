package via.dk.cueandbrew.client;

import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface CallbackClient
{
  void onLogin(String login, String password) throws RemoteException;
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  void addPropertyChange(PropertyChangeListener listener);
  void onSearch(String phone) throws RemoteException;
}
