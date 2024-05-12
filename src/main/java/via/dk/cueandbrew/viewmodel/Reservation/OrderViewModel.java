package via.dk.cueandbrew.viewmodel.Reservation;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class OrderViewModel
{
  private Model model;
  private ViewHandler viewHandler;

  public OrderViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onCancel()
  {
    this.viewHandler.openManagerMainPage();
  }

  public void onBackToReservations()
  {
    this.viewHandler.openCreateReservationView();
  }

  public void onSkip()
  {
    this.viewHandler.openFinalizeReservationView();
  }

  public void onConfirm()
  {
    this.viewHandler.openFinalizeReservationView();
  }
}
