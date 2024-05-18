package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.Toast;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

public class FinalizeReservationViewModel {
    private final Model model;
    private final ViewHandler viewHandler;

    public FinalizeReservationViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
    }

    public void updateDateTime(Label date, Label time) {
        this.model.updateDateTime(date, time);
    }

    public void startDateTimeUpdater(Label date, Label time) {
        this.model.startDateTimeUpdater(date, time);
    }

    public void onCancel() {
        this.viewHandler.closeFinalizeReservationView();
    }

    public Reservation onFinalize(String firstname, String lastname, String phoneNumber, String notes) {
        this.model.getReservationBuilder()
                .setClientFirstName(firstname)
                .setClientLastName(lastname)
                .setClientPhoneNumber(phoneNumber)
                .setNotes(notes);
        try {
            this.model.onFinalizeReservation();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.viewHandler.closeFinalizeReservationView();
        if(Registration.getInstance().getManager_id() != -1){
            this.viewHandler.openManagerMainPage();
        } else {
            this.viewHandler.openUserMainPageView();
        }
        return this.model.getReservationBuilder().build();
    }
}
