package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.Toast;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

public class FinalizeReservationViewModel {
    private Model model;
    private ViewHandler viewHandler;

    public FinalizeReservationViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
    }

    public void onCancel() {
        this.viewHandler.closeFinalizeReservationView();
    }

    public void onFinalize(String firstname, String lastname, String phonenumber, String notes) {
        this.model.getReservationBuilder()
                .setClientFirstName(firstname)
                .setClientLastName(lastname)
                .setClientPhoneNumber(phonenumber)
                .setNotes(notes);
        try {
            this.model.onFinalizeReservation();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(this.viewHandler.getStage(), "Reservation created!");
        this.viewHandler.closeFinalizeReservationView();
        this.viewHandler.openManagerMainPage();
    }
}
