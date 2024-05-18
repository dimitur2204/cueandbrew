package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.*;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that is responsible for the CreateReservationViewModel
 * @Author Marius Marcoci, Darja Jefromova
 */
public class CreateReservationViewModel {
    private final Model model;
    private final ViewHandler viewHandler;
    private LocalDateTime dateTime;
    private int duration;
    private final ArrayList<Integer> tableNumbers;

    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
    public CreateReservationViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.tableNumbers = new ArrayList<>();
    }

    /**
     * A method that updates the date and time
     * @param date The date
     * @param time The time
     */
    public void updateDateTime(Label date, Label time) {
        this.model.updateDateTime(date, time);
    }

    /**
     * A method that starts the date time updater
     * @param date The date
     * @param time The time
     */
    public void startDateTimeUpdater(Label date, Label time) {
        this.model.startDateTimeUpdater(date, time);
    }

    /**
     * A method that chooses the date and time
     * @param dateTime The date and time
     */
    public void chooseDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * A method that chooses the duration
     * @param duration The duration
     */
    public void chooseDuration(int duration) {
        this.duration = duration;
    }

    /**
     * A method that chooses the table
     * @param tableNumber The table number
     */
    public void chooseTable(int tableNumber) {
        this.tableNumbers.add(tableNumber);
    }

    /**
     * Move to the next view
     */
    public void onNext() {
        LocalDateTime endtime = this.dateTime.plusMinutes(this.duration);
        Booking booking = new Booking(Date.valueOf(this.dateTime.toLocalDate()), Time.valueOf(this.dateTime.toLocalTime()), Time.valueOf(endtime.toLocalTime()));
        for (int tableNumber : this.tableNumbers) {
            booking.getTables().add(new Table(tableNumber));
        }
        this.model.getReservationBuilder()
                .setBooking(booking);
        this.viewHandler.openOrder();
    }

    /**
     * A method that returns to the previous view
     */
    public void onCancel() {
        if (Registration.getInstance().getManager_id() != -1) {
            System.out.println(Registration.getInstance().getManager_id());
            this.viewHandler.openManagerMainPage();
        }
        else {
            System.out.println(Registration.getInstance().getManager_id());
            this.viewHandler.openUserMainPageView();
        }
    }

    /**
     * A method that gets the unavailable table ids
     * @param dateTime The date and time
     * @param durationMinutes The duration
     * @return The list of unavailable table ids
     * @throws RemoteException If there is an error
     */
    public List<Integer> getUnavailableTableIds(LocalDateTime dateTime, int durationMinutes) throws RemoteException {
        List<Reservation> reservations = model.getReservationsByDateTimeAndDuration(dateTime, durationMinutes);
        // get all bookings from all reservations
        List<Booking> bookings = reservations.stream().map(Reservation::getBooking).toList();
        //get all tables from all bookings
        List<Table> tables = bookings.stream().flatMap(booking -> booking.getTables().stream()).toList();
        //get all table numbers
        return tables.stream().map(Table::getNumber).collect(Collectors.toList());
    }
}
