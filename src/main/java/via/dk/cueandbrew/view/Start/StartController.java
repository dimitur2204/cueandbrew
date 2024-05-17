package via.dk.cueandbrew.view.Start;

import via.dk.cueandbrew.viewmodel.Start.StartViewModel;

import java.util.UUID;

public class StartController
{
  private StartViewModel viewModel;

  public void init(StartViewModel viewModel) {
    this.viewModel = viewModel;
    this.viewModel.sendIDToLoginController(UUID.randomUUID());
  }

  public void onUser()
  {
    this.viewModel.onUser();
  }

  public void onManager()
  {
    this.viewModel.onManager();
  }
}
