package via.dk.cueandbrew.client;

import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * An interface that defines the methods that are avaliable for interacting with the client
 */
public interface CallbackClient
{
  void onLogin(String login, String password, UUID id) throws RemoteException;
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException;
  void addPropertyChange(PropertyChangeListener listener);
  List<Reservation> onSearch(String phone) throws RemoteException;
  boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;
  List<Notification> fetchNotifications() throws RemoteException;
  void addDrink(String name, double price, int quantity) throws RemoteException;
  void markNotificationAsRead(Notification notification) throws RemoteException;
  void createNotification(Notification message) throws RemoteException;
}
