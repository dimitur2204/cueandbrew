package via.dk.cueandbrew.model;

import javafx.scene.control.Label;
import via.dk.cueandbrew.shared.Feedback;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Model {
    void onLogin(String login, String password, UUID id);

    void addPropertyChangeListener(PropertyChangeListener listener);

    Reservation.ReservationBuilder getReservationBuilder();

    List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException;

    List<Reservation> onSearch(String phone) throws RemoteException;

    void onFinalizeReservation() throws RemoteException;

    void addDrink(String name, double price, int quantity);
    Feedback createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException;

    void startDateTimeUpdater(Label date, Label time);

    void updateDateTime(Label date, Label time);

    List<Notification> fetchNotifications() throws RemoteException;

    void markNotificationAsRead(Notification notification) throws RemoteException;

    void createNotification(Notification notification) throws RemoteException;
  boolean cancelReservation(int id) throws RemoteException;
  List<Feedback> fetchFeedbacks();
    boolean checkFeedback(int managerId, int feedbackId);
}
