package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class UserMainPageViewModel
{
  private Model model;
  private final ViewHandler viewHandler;

  public UserMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onMakeAReservation() {
    this.viewHandler.openCreateReservationView();
  }

  public void onClose()
  {
    this.viewHandler.openStartView();
  }
}
