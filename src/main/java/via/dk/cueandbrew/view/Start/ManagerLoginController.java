package via.dk.cueandbrew.view.Start;

import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;

public class ManagerLoginController
{
  private ManagerLoginViewModel viewModel;

  public void init(ManagerLoginViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public void onCancel() {
    this.viewModel.onCancel();
  }

  public void onLogin() {
    this.viewModel.onLogin();
  }
}
