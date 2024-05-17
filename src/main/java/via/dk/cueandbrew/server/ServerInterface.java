package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface ServerInterface extends Remote
{
  void onLogin(String login, String password) throws RemoteException;
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException;
  void addRegistrationPropertyChangeListener(RemotePropertyChangeListener<Registration> listener) throws RemoteException;
  List<Reservation> onSearch(String phone) throws RemoteException;
  void addReservationPropertyChangeListener(RemotePropertyChangeListener<Reservation> listener) throws RemoteException;
  boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;
}
