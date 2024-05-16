package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

public class CreateFeedbackViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;

  public CreateFeedbackViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

  public void onCancel() {
    this.viewHandler.closeCreateFeedbackStage();
  }

  public boolean onFinalize(String content, String selectedType, String firstname, String lastname) throws RemoteException
  {
    return this.model.createFeedback(content, selectedType, firstname, lastname);
  }
}
