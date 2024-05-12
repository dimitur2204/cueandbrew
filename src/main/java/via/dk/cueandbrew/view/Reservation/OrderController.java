package via.dk.cueandbrew.view.Reservation;

import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

public class OrderController
{
  private OrderViewModel viewModel;

  public void init(OrderViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onBackToReservations() {
    this.viewModel.onBackToReservations();
  }

  public void onSkip() {
    this.viewModel.onSkip();
  }

  public void onConfirm() {
    this.viewModel.onConfirm();
  }
}
