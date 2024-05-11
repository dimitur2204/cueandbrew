package via.dk.cueandbrew.view.Start;

import javafx.scene.layout.Region;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Start.StartViewModel;

public class StartController
{
  private StartViewModel viewModel;

  public void init(StartViewModel viewModel) {
    this.viewModel = viewModel;
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
