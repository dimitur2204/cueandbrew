package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A class that is responsible for the Reservation
 * @Author Darja Jefremova, Dimitar Nizamov
 */
public class Reservation implements Serializable {
    private final String notes;
    private final String clientFirstName;
    private final String clientLastName;
    private final String clientPhoneNumber;
    private final Timestamp creationDatetime;
    private final Booking booking;
    private final Order order;
    private int reservationId;
    private final int wasCancelled;

    /**
     * A private constructor that initializes the Reservation with the specified builder
     * @param builder The builder of the reservation
     */
    private Reservation(ReservationBuilder builder) {
        this.clientFirstName = builder.clientFirstName;
        this.clientLastName = builder.clientLastName;
        this.clientPhoneNumber = builder.clientPhoneNumber;
        this.notes = builder.notes;
        this.creationDatetime = builder.creationDatetime;
        this.booking = builder.booking;
        this.order = builder.order;
        this.reservationId = builder.reservationId;
        this.wasCancelled = builder.wasCancelled;
    }

    /**
     * A method that returns the reservation id
     * @return The id of the reservation
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    /**
     * A method that returns the reservation id
     * @return The id of the reservation
     */
    public int getReservationId()
    {
        return reservationId;
    }

    /** A method that returns the booking
     * @return The booking of the reservation
     */
    public Booking getBooking() {
        return booking;
    }


    /**
     * A method that returns the first name of the client
     * @return The first name of the client
     */
    public String getClientFirstName() {
        return clientFirstName;
    }

    /**
     * A method that returns the last name of the client
     * @return The last name of the client
     */
    public String getClientLastName() {
        return clientLastName;
    }

    /**
     * A method that returns the phone number of the client
     * @return The phone number of the client
     */
    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    /**
     * A method that returns the creation date and time
     * @return The creation date and time of the reservation
     */
    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    /**
     * A method that returns the notes
     * @return The notes of the reservation
     */
    public String getNotes() {
        return notes;
    }

    /**
     * A method that returns the order
     * @return The order of the reservation
     */
    public Order getOrder() {
        return order;
    }

    public int getWasCancelled()
    {
        return wasCancelled;
    }

    /**
     * A builder class that is responsible for the Reservation
     * @author Dimitar Nizamov
     */
    public static class ReservationBuilder implements Serializable {
        private String clientFirstName;
        private String clientLastName;
        private String clientPhoneNumber;
        private String notes;
        private Timestamp creationDatetime;
        private Booking booking;
        private Order order;
        private int reservationId;
        private int wasCancelled;

        /**
         * A constructor that initializes the ReservationBuilder with the current time
         */
        public ReservationBuilder() {
            this.creationDatetime = new Timestamp(System.currentTimeMillis());
        }

        /**
         * A method that sets the notes
         * @param notes The notes of the reservation
         * @return The notes of the reservation
         */
        public ReservationBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        /**
         * A method that sets the booking
         * @param booking The booking of the reservation
         * @return The booking of the reservation
         */
        public ReservationBuilder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        /**
         * A method that sets the order
         * @param order The order of the reservation
         * @return The order of the reservation
         */
        public ReservationBuilder setOrder(Order order) {
            this.order = order;
            return this;
        }

        /**
         * A method that sets the first name of the client
         * @param clientFirstName The first name of the client
         * @return The first name of the client
         */
        public ReservationBuilder setClientLastName(String clientLastName) {
            this.clientLastName = clientLastName;
            return this;
        }

        /**
         * A method that sets the first name of the client
         * @param clientFirstName The first name of the client
         * @return The first name of the client
         */
        public ReservationBuilder setClientFirstName(String clientFirstName) {
            this.clientFirstName = clientFirstName;
            return this;
        }

        /**
         * A method that sets the creation date and time
         * @param creationDatetime The creation date and time of the reservation
         * @return The creation date and time of the reservation
         */
        public ReservationBuilder setCreationDatetime(Timestamp creationDatetime) {
            this.creationDatetime = creationDatetime;
            return this;
        }

        /**
         * A method that sets the phone number of the client
         * @param clientPhoneNumber The phone number of the client
         * @return The phone number of the client
         */
        public ReservationBuilder setClientPhoneNumber(String clientPhoneNumber) {
            this.clientPhoneNumber = clientPhoneNumber;
            return this;
        }

        /**
         * A method that sets the reservation id
         * @param reservationId The id of the reservation
         * @return The id of the reservation
         */
        public ReservationBuilder setReservationId(int reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public ReservationBuilder setWasCancelled(int wasCancelled) {
            this.wasCancelled = wasCancelled;
            return this;
        }

        /**
         * A method that builds the reservation based on the specified builder
         * @return The reservation
         */
        public Reservation build() {
            return new Reservation(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Reservation other = (Reservation) obj;
        return other.getBooking().equals(this.getBooking()) && other.getClientFirstName().equals(this.getClientFirstName()) && other.getClientLastName().equals(this.getClientLastName());
    }

    @Override
    public String toString() {
        return clientFirstName + " " + clientLastName + " booked table " + booking.getTables().getFirst() + " for " + booking.getStartTime() + " until " + booking.getEndTime();
    }
}