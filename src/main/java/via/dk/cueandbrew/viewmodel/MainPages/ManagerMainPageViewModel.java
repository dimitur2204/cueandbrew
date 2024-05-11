package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class ManagerMainPageViewModel
{
  private Model model;
  private ViewHandler viewHandler;

  public ManagerMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

  public void onExit()
  {
    this.viewHandler.openManagerLoginView();
  }
}
