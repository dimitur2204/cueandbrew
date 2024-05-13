package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
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
        return reservations.stream()
                .map(Reservation::getTableId)
                .collect(Collectors.toList());
    }
}
