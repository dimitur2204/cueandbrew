package via.dk.cueandbrew.shared;

import java.io.Serializable;

/**
 * A class that is responsible for the Notification
 * @Author Dimitar Nizamov
 */
public class Notification implements Serializable {
    private int notificationId;
    private Reservation reservation;
    private int wasSeen;

    /**
     * A constructor that initializes the Notification with the specified values
     * @param reservation The reservation of the notification
     */
    public Notification(Reservation reservation) {
        this.reservation = reservation;
        this.wasSeen = 0;
        this.notificationId = -1;
    }

    /**
     * A method that sets the notification id
     * @param notificationId The id of the notification
     * @return The id of the notification
     */
    public int setNotificationId(int notificationId) {
        return this.notificationId = notificationId;
    }

    /**
     * A method that returns the notification id
     * @return The id of the notification
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * A method that returns the reservation
     * @return The reservation of the notification
     */
    public int getWasSeen() {
        return wasSeen;
    }

    /**
     * A method that sets the reservation
     * @param wasSeen The reservation of the notification
     */
    public void setWasSeen(int wasSeen) {
        this.wasSeen = wasSeen;
    }

    /**
     * A method that sets the reservation
     * @param reservation The reservation of the notification
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * A method that returns the reservation
     * @return The reservation of the notification
     */
    public Reservation getReservation() {
        return reservation;
    }
}
