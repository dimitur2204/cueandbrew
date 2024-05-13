package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.time.LocalDateTime;

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

    public void onCancel() {
        //need a check here if a manager or a user is logged in
        //based on this check -> open the relevant views
        this.viewHandler.openManagerMainPage();
    }
}
