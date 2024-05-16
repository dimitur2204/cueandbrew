package via.dk.cueandbrew.model;
import via.dk.cueandbrew.shared.Reservation;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface Model
{
  void onLogin(String login, String password);
  void addPropertyChangeListener(PropertyChangeListener listener);
  Reservation.ReservationBuilder getReservationBuilder();
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  List<Reservation> onSearch(String phone) throws RemoteException;
  void onFinalizeReservation() throws RemoteException;
}
