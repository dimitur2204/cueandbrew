package via.dk.cueandbrew.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import via.dk.cueandbrew.shared.Feedback;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * An interface that defines the methods that the server can call
 */
public interface ServerInterface extends Remote
{
  void onLogin(String login, String password, UUID id) throws RemoteException;
  List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;
  void onFinalizeReservation(Reservation.ReservationBuilder builder) throws RemoteException;
  void addRegistrationPropertyChangeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  void addFeedbackPropertyChangeListener(
      RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  List<Reservation> onSearch(String phone) throws RemoteException;
  void addReservationPropertyChangeListener(RemotePropertyChangeListener<Serializable> listener) throws RemoteException;
  Feedback createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;
  List<Notification> fetchNotifications() throws RemoteException;
  void markNotificationAsRead(Notification notification) throws RemoteException;
  void createNotification(Notification notification) throws RemoteException;
  boolean cancelReservation(int id) throws RemoteException;
  List<Feedback> fetchFeedbacks() throws RemoteException;
  boolean checkFeedback(int managerId, int feedbackId) throws RemoteException;
  boolean onAddDrink(String name, Double price, int quantity) throws RemoteException;
}
