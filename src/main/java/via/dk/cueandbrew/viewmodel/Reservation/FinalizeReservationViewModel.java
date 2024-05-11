package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class FinalizeReservationViewModel
{
  private Model model;
  private ViewHandler viewHandler;

  public FinalizeReservationViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onCancel()
  {
    this.viewHandler.closeFinalizeReservationView();
  }

  public void onFinalize() {
    this.viewHandler.closeFinalizeReservationView();
    this.viewHandler.openManagerMainPage();
  }
}
