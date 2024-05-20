package via.dk.cueandbrew.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;
import via.dk.cueandbrew.client.CallbackClient;
import via.dk.cueandbrew.shared.Feedback;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * A class that is responsible for the Model Manager implementing the Model Interface
 * It is responsible for the implementation of the methods that the client can call on the server
 *
 * @author Dimitar Nizamov, Darja Jefremova, Andreea Caisim, Marius Marcoci
 */
public class ModelManager implements Model, PropertyChangeListener {
    private final CallbackClient client;
    private final PropertyChangeSupport support;
    private final Reservation.ReservationBuilder reservationBuilder;

    /**
     * A constructor that initializes the Model Manager
     *
     * @param client The client that is used
     */
    public ModelManager(CallbackClient client) {
        this.client = client;
        this.client.addPropertyChange(this);
        this.reservationBuilder = new Reservation.ReservationBuilder();
        this.support = new PropertyChangeSupport(this);
    }


    /**
     * A method that returns the common reservation builder
     * Responsible for constructing the new reservation
     *
     * @return The reservation builder
     */
    public Reservation.ReservationBuilder getReservationBuilder() {
        return reservationBuilder;
    }


    /**
     * A method that is called when the client logs in
     *
     * @param login    The login of the client
     * @param password The password of the client
     * @param id       The id of the client
     */
    @Override
    public void onLogin(String login, String password, UUID id) {
        try {
            this.client.onLogin(login, password, id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * A method that returns the reservations in the range by the date and time and the duration
     *
     * @param start           The start date and time
     * @param durationMinutes The duration of the reservation
     * @return The list of reservations
     */
    @Override
    public List<Reservation> getReservationsByDateTimeAndDuration(LocalDateTime start, int durationMinutes) throws RemoteException {
        return this.client.getReservationsByDateTimeAndDuration(start, durationMinutes);
    }

    /**
     * A method that is called when the client finalizes the reservation
     */
    @Override
    public void onFinalizeReservation() throws RemoteException {
        this.client.onFinalizeReservation(this.reservationBuilder);
    }

    /**
     * A method that is called when the client searches for a reservation by phone
     *
     * @param phone The phone number of the client
     * @return The list of reservations
     */

    @Override
    public List<Reservation> onSearch(String phone) throws RemoteException {
        return this.client.onSearch(phone);
    }

    @Override
    public void addDrink(String name, double price, int quantity) {
        try {
            this.client.addDrink(name, price, quantity);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that creates a feedback
     *
     * @param content      The content of the feedback
     * @param selectedType The type of the feedback
     * @param firstname    The first name of the client
     * @param lastname     The last name of the client
     * @return True if the feedback is created, false otherwise
     */
    @Override
    public boolean createFeedback(String content, String selectedType, String firstname, String lastname) throws RemoteException {
        return this.client.createFeedback(content, selectedType, firstname, lastname);
    }

    /**
     * A method that starts the date and time updater
     * It uses a timeline to update the date and time every second
     *
     * @param date The label that shows the date
     * @param time The label that shows the time
     */
    @Override
    public void startDateTimeUpdater(Label date, Label time) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateDateTime(date, time))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * A method that updates the date and time
     *
     * @param date The label that shows the date
     * @param time The label that shows the time
     */
    @Override
    public void updateDateTime(Label date, Label time) {
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

    /**
     * A method that fetches the notifications
     *
     * @return The list of notifications
     */
    @Override
    public List<Notification> fetchNotifications() throws RemoteException {
        return this.client.fetchNotifications();
    }

    /**
     * A method that marks a notification as read
     *
     * @param notification The notification to be marked as read
     */
    @Override
    public void markNotificationAsRead(Notification notification) throws RemoteException {
        this.client.markNotificationAsRead(notification);
    }

    /**
     * A method that creates a notification
     *
     * @param notification The notification to be created
     */
    @Override
    public void createNotification(Notification notification) throws RemoteException {
        this.client.createNotification(notification);
    }

    @Override
    public boolean cancelReservation(int id) throws RemoteException {
        boolean result = this.client.cancelReservation(id);
        //not sure if this if statement is needed
        if (result) {
            this.reservationBuilder.setWasCancelled(1);
        }
        return result;
    }

    @Override
    public List<Feedback> fetchFeedbacks() {
        try {
            return this.client.fetchFeedbacks();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkFeedback(int managerId, int feedbackId) {
        try {
            return this.client.checkFeedback(managerId, feedbackId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("login")) {
                Registration temp = (Registration) evt.getNewValue();
                if (temp.getLogin() != null) {
                    //there is a registration
                    this.support.firePropertyChange("login", null, "true");
                    this.support.firePropertyChange("welcome", null, temp.getLogin());
                } else {
                    //there isn't a registration
                    this.support.firePropertyChange("login", null, "false");
                }
            } else if (evt.getPropertyName().equals("reservation_created")) {
                this.support.firePropertyChange("reservation_created", null, evt.getNewValue());
            } else if (evt.getPropertyName().equals("created_feedback")) {
                this.support.firePropertyChange("created_feedback", null, evt.getNewValue());
            } else if (evt.getPropertyName().equals("check_feedback")) {
                this.support.firePropertyChange("check_feedback", null, evt.getNewValue());
            }
        });
    }
}
