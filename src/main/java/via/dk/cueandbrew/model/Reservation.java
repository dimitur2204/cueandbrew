package via.dk.cueandbrew.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private String notes;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Timestamp creationDatetime;
    private List<Booking> booking;
    private List<Order> order;

    public Reservation() {
        this.notes = null;
        this.clientFirstName = null;
        this.clientLastName = null;
        this.clientPhoneNumber = null;
        this.creationDatetime = null;
        this.booking = new ArrayList<>();
        this.order = new ArrayList<>();
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setCreationDatetime(Timestamp creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Order> getOrder() {
        return order;
    }
}

