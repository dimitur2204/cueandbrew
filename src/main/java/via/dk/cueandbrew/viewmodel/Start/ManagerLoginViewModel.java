package via.dk.cueandbrew.viewmodel.Start;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

public class ManagerLoginViewModel
{
  private Model model;
  private ViewHandler viewHandler;

  public ManagerLoginViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onCancel()
  {
    this.viewHandler.openStartView();
  }

  public void onLogin()
  {
    this.viewHandler.openManagerMainPage();
  }
}
