package via.dk.cueandbrew.viewmodel.Reservation;

import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.Toast;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

/**
 * A class that is responsible for the FinalizeReservationViewModel
 * @author Dimitar Nizamov
 */
public class FinalizeReservationViewModel {
    private final Model model;
    private final ViewHandler viewHandler;

    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
    public FinalizeReservationViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
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
     * A method that cancels the finalize reservation
     */
    public void onCancel() {
        this.viewHandler.closeFinalizeReservationView();
    }

    /**
     * A method that finalizes the reservation
     * @param firstname The first name
     * @param lastname The last name
     * @param phoneNumber The phone number
     * @param notes The notes
     * @return The reservation
     */
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
