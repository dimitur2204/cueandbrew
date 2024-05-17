package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reservation implements Serializable {
    private final String notes;
    private final String clientFirstName;
    private final String clientLastName;
    private final String clientPhoneNumber;
    private final Timestamp creationDatetime;
    private final Booking booking;
    private final Order order;
    private final int reservationId;

    private Reservation(ReservationBuilder builder) {
        this.clientFirstName = builder.clientFirstName;
        this.clientLastName = builder.clientLastName;
        this.clientPhoneNumber = builder.clientPhoneNumber;
        this.notes = builder.notes;
        this.creationDatetime = builder.creationDatetime;
        this.booking = builder.booking;
        this.order = builder.order;
        this.reservationId = builder.reservationId;
    }



    public int getReservationId()
    {
        return reservationId;
    }

    public Booking getBooking() {
        return booking;
    }


    public String getClientFirstName() {
        return clientFirstName;
    }


    public String getClientLastName() {
        return clientLastName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public String getNotes() {
        return notes;
    }

    public Order getOrder() {
        return order;
    }

    public static class ReservationBuilder implements Serializable {
        private String clientFirstName;
        private String clientLastName;
        private String clientPhoneNumber;
        private String notes;
        private Timestamp creationDatetime;
        private Booking booking;
        private Order order;
        private int reservationId;

        public ReservationBuilder() {
            this.creationDatetime = new Timestamp(System.currentTimeMillis());
        }

        public ReservationBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public ReservationBuilder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public ReservationBuilder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public ReservationBuilder setClientLastName(String clientLastName) {
            this.clientLastName = clientLastName;
            return this;
        }

        public ReservationBuilder setClientFirstName(String clientFirstName) {
            this.clientFirstName = clientFirstName;
            return this;
        }

        public ReservationBuilder setCreationDatetime(Timestamp creationDatetime) {
            this.creationDatetime = creationDatetime;
            return this;
        }

        public ReservationBuilder setClientPhoneNumber(String clientPhoneNumber) {
            this.clientPhoneNumber = clientPhoneNumber;
            return this;
        }

        public ReservationBuilder setReservationId(int reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }
    @Override
    public String toString() {
        return clientFirstName + " " + clientLastName + " booked table " + booking.getTables().getFirst() + " for " + booking.getStartTime() + " until " + booking.getEndTime();
    }
}