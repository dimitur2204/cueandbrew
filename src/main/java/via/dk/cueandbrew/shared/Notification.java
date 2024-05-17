package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Notification implements Serializable {
    private int notificationId;
    private Reservation reservation;
    private int wasSeen;

    public Notification(Reservation reservation) {
        this.reservation = reservation;
        this.wasSeen = 0;
        this.notificationId = -1;
    }

    public int setNotificationId(int notificationId) {
        return this.notificationId = notificationId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public int getWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(int wasSeen) {
        this.wasSeen = wasSeen;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
