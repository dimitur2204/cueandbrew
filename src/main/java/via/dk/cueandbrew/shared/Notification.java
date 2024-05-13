package via.dk.cueandbrew.shared;

public class Notification {
    private Reservation reservation;
    public Notification(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
