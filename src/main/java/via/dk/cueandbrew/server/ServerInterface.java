package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import via.dk.cueandbrew.client.CallbackClientImplementation;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ServerInterface extends Remote
{
  void onLogin(String login, String password, UUID id) throws RemoteException;
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException;
  void addRegistrationPropertyChangeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  List<Reservation> onSearch(String phone) throws RemoteException;
  void addReservationPropertyChangeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;
  List<Notification> fetchNotifications() throws RemoteException;
  void markNotificationAsRead(Notification notification) throws RemoteException;
  void createNotification(Notification notification) throws RemoteException;
}
