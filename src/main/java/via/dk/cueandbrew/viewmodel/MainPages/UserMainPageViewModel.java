package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;
import java.util.List;

/**
 * A class that is responsible for the UserMainPageViewModel
 * @Author Dimitar Nizamov, Andreea Caisim
 */
public class UserMainPageViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;

    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
  public UserMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

    /**
     * A method that opens the create reservation view
     */
  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

    /**
     * A method that opens the create feedback view
     */
  public void openCreateFeedbackPage() {
    this.viewHandler.openCreateFeedbackStage();
  }

    /**
     * A method that closes to the start view
     */
  public void onClose()
  {
    this.viewHandler.getViewModelFactory().getManagerLoginViewModel().clearErrorLabel();
    this.viewHandler.openStartView();
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
     * A method that updates the date and time
     * @param date The date
     * @param time The time
     */
  public void updateDateTime(Label date, Label time) {
    this.model.updateDateTime(date, time);
  }

    /**
     * A method that searches for a reservation
     * @param phone The phone
     * @return The reservations
     * @throws RemoteException
     */
  public List<Reservation> onSearch(String phone) throws RemoteException
  {
    return this.model.onSearch(phone);
  }
}
