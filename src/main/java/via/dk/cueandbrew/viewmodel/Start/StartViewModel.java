package via.dk.cueandbrew.viewmodel.Start;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class StartViewModel
{
  private Model model;
  private ViewHandler viewHandler;

  public StartViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onUser() {
    this.viewHandler.openUserMainPageView();
  }

  public void onManager() {
    this.viewHandler.openManagerLoginView();
  }
}
