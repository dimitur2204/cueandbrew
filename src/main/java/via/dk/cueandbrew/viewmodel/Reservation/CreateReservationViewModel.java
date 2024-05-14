package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Booking;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.Table;
import via.dk.cueandbrew.view.ViewHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateReservationViewModel {
    private Model model;
    private final ViewHandler viewHandler;

    private LocalDateTime dateTime;
    private int duration;
    private int tableId;

    public CreateReservationViewModel(Model model, ViewHandler viewHandler)
    {
        this.model = model;
        this.viewHandler = viewHandler;
    }
    public void chooseDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void chooseDuration(int duration) {
        this.duration = duration;
    }

    public void chooseTable(int tableId) {
        this.tableId = tableId;
    }

    public void onNext() {
        this.viewHandler.openOrder();
    }
    public List<Table> getOccupiedTables(){
        return new ArrayList<>();
    }

    public void onCancel() {
        //need a check here if a manager or a user is logged in
        //based on this check -> open the relevant views
        this.viewHandler.openManagerMainPage();
    }
    public List<Integer> getUnavailableTableIds(LocalDateTime dateTime, int durationMinutes) {
        List<Reservation> reservations = model.getReservationsByDateTimeAndDuration(dateTime, durationMinutes);
        // get all bookings from all reservations
        List<Booking> bookings = reservations.stream().flatMap(reservation -> reservation.getBooking().stream()).collect(Collectors.toList());
        //get all tables from all bookings
        List<Table> tables = bookings.stream().flatMap(booking -> booking.getTables().stream()).collect(Collectors.toList());
        //get all table numbers
        return tables.stream().map(Table::getNumber).collect(Collectors.toList());
    }
}
