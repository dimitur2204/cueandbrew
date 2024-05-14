package via.dk.cueandbrew.shared;

import java.sql.Timestamp;
import java.util.List;

public class Reservation {
    private String notes;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Timestamp creationDatetime;
    private List<Booking> booking;
    private Order order;
    private int bookingId;

    private Reservation(ReservationBuilder builder) {
        this.clientFirstName = builder.clientFirstName;
        this.clientLastName = builder.clientLastName;
        this.clientPhoneNumber = builder.clientPhoneNumber;
        this.notes = builder.notes;
        this.creationDatetime = builder.creationDatetime;
        this.booking = builder.booking;
        this.order = builder.order;
    }


    public List<Booking> getBooking() {
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

    public int getBookingId() {
        return this.bookingId;
    }

    public static class ReservationBuilder {
        private String clientFirstName;
        private String clientLastName;
        private String clientPhoneNumber;
        private String notes;
        private Timestamp creationDatetime;
        private List<Booking> booking;
        private Order order;

        public ReservationBuilder(String clientFirstName, String clientLastName, String clientPhoneNumber) {
            this.creationDatetime = new Timestamp(System.currentTimeMillis());
            this.clientFirstName = clientFirstName;
            this.clientLastName = clientLastName;
            this.clientPhoneNumber = clientPhoneNumber;
        }

        public ReservationBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public ReservationBuilder setBooking(List<Booking> booking) {
            this.booking = booking;
            return this;
        }

        public ReservationBuilder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }
    @Override
    public String toString() {
        return clientFirstName + " " + clientLastName + " booked table " + booking.getFirst().getTables().getFirst() + " for " + booking.getFirst().getStartTime() + " until " + booking.getFirst().getEndTime();
    }



}

