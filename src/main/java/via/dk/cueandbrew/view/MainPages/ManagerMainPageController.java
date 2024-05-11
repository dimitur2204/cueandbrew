package via.dk.cueandbrew.view.MainPages;

import via.dk.cueandbrew.viewmodel.MainPages.ManagerMainPageViewModel;

public class ManagerMainPageController
{
  private ManagerMainPageViewModel viewModel;

  public void init(ManagerMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void onExit() {
    this.viewModel.onExit();
  }

  public void onMakeAReservation() {
    this.viewModel.onMakeAReservation();
  }
}
