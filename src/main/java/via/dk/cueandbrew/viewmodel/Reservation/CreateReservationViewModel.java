package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.Table;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateReservationViewModel {
    private Model model;
    private final ViewHandler viewHandler;
    private LocalDateTime dateTime;
    private int duration;
    private ArrayList<Integer> tableNumbers;

    public CreateReservationViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.tableNumbers = new ArrayList<>();
    }

    public void chooseDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void chooseDuration(int duration) {
        this.duration = duration;
    }

    public void chooseTable(int tableNumber) {
        this.tableNumbers.add(tableNumber);
    }

    public void onNext() {
        LocalDateTime endtime = this.dateTime.plusMinutes(this.duration);
        Booking booking = new Booking(Date.valueOf(this.dateTime.toLocalDate()), Time.valueOf(this.dateTime.toLocalTime()), Time.valueOf(endtime.toLocalTime()));
        this.model.getReservationBuilder();
        this.viewHandler.openOrder();
    }

    public void onCancel() {
        //need a check here if a manager or a user is logged in
        //based on this check -> open the relevant views
        this.viewHandler.openManagerMainPage();
    }

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
