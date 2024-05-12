package via.dk.cueandbrew.view.Reservation;

import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;

public class FinalizeReservationController
{
  private FinalizeReservationViewModel viewModel;

  public void init(FinalizeReservationViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onFinalize() {
    this.viewModel.onFinalize();
  }
}
