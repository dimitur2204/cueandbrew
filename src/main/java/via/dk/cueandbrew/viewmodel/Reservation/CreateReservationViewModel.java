package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class CreateReservationViewModel {
    private Model model;
    private final ViewHandler viewHandler;

    public CreateReservationViewModel(Model model, ViewHandler viewHandler)
    {
        this.model = model;
        this.viewHandler = viewHandler;
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
