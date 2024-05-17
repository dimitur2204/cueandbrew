package via.dk.cueandbrew.viewmodel.Start;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.util.UUID;

public class StartViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;

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

  public void sendIDToLoginController(UUID id)
  {
    this.viewHandler.getViewModelFactory().getManagerLoginViewModel().setId(id);
  }
}
