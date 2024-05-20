package via.dk.cueandbrew.viewmodel.Start;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

import java.util.UUID;

/**
 * A class that is responsible for the StartViewModel
 *
 * @author Marius Marcoci
 */
public class StartViewModel {
    private final Model model;
    private final ViewHandler viewHandler;

    /**
     * A constructor that sets the model and the viewHandler
     *
     * @param model       The model
     * @param viewHandler The viewHandler
     */
    public StartViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
    }

    /**
     * A method that opens the user main page view
     */
    public void onUser() {
        this.viewHandler.openUserMainPageView();
    }

    /**
     * A method that opens the manager login view
     */
    public void onManager() {
        this.viewHandler.openManagerLoginView();
    }


    /**
     * A method that sends the id to the login controller
     *
     * @param id The id
     */
    public void sendIDToLoginController(UUID id) {
        this.viewHandler.getViewModelFactory().getManagerLoginViewModel().setId(id);
    }
}
