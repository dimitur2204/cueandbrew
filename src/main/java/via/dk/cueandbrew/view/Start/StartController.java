package via.dk.cueandbrew.view.Start;

import via.dk.cueandbrew.viewmodel.Start.StartViewModel;

import java.util.UUID;

/**
 * A class that is responsible for the StartController
 * @Author Marius Marcoci
 */
public class StartController
{
  private StartViewModel viewModel;

    /**
     * A method that initializes the StartViewModel
     * @param viewModel The StartViewModel
     */
  public void init(StartViewModel viewModel) {
    this.viewModel = viewModel;
    this.viewModel.sendIDToLoginController(UUID.randomUUID());
  }

    /**
     * A method that opens the user main page view
     */
  public void onUser()
  {
    this.viewModel.onUser();
  }

    /**
     * A method that opens the manager login view
     */
  public void onManager()
  {
    this.viewModel.onManager();
  }
}
