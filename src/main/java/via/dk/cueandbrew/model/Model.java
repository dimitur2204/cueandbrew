package via.dk.cueandbrew.model;
import javafx.scene.control.Label;
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
  boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;
  void startDateTimeUpdater(Label date, Label time);
  void updateDateTime(Label date, Label time);
}
