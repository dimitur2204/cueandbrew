package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;
import java.util.List;

public class UserMainPageViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;

  public UserMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

  public void openCreateFeedbackPage() {
    this.viewHandler.openCreateFeedbackStage();
  }

  public void onClose()
  {
    this.viewHandler.getViewModelFactory().getManagerLoginViewModel().clearErrorLabel();
    this.viewHandler.openStartView();
  }

  public void startDateTimeUpdater(Label date, Label time) {
    this.model.startDateTimeUpdater(date, time);
  }

  public void updateDateTime(Label date, Label time) {
    this.model.updateDateTime(date, time);
  }

  public List<Reservation> onSearch(String phone) throws RemoteException
  {
    return this.model.onSearch(phone);
  }

  public boolean cancelReservation(int id) throws RemoteException
  {
    return this.model.cancelReservation(id);
  }
}
