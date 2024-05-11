package via.dk.cueandbrew.view.MainPages;

import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;

public class UserMainPageController
{
  private UserMainPageViewModel viewModel;

  public void init(UserMainPageViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public void onMakeAReservation() {
    this.viewModel.onMakeAReservation();
  }

  public void onClose() {
    this.viewModel.onClose();
  }
}
