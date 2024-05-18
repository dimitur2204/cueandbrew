package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

/**
 * A class that is responsible for the CreateFeedbackViewModel
 * @author Marius Marcoci
 */
public class CreateFeedbackViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;


    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
  public CreateFeedbackViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
  }

    /**
     * A method that closes the create feedback stage
     */
  public void onCancel() {
    this.viewHandler.closeCreateFeedbackStage();
  }

    /**
     * A method that finalizes the feedback
     * @param content The content
     * @param selectedType The selected type
     * @param firstname The firstname
     * @param lastname The lastname
     * @return A boolean
     * @throws RemoteException
     */
  public boolean onFinalize(String content, String selectedType, String firstname, String lastname) throws RemoteException
  {
    return this.model.createFeedback(content, selectedType, firstname, lastname);
  }
}
