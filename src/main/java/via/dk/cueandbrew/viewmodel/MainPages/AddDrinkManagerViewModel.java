package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;

/**
 * A class that is responsible for the AddDrinkManagerViewModel
 * @author Andreea Caisim
 */
public class AddDrinkManagerViewModel
{
    private final Model model;
    private final ViewHandler viewHandler;

    /** A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
    public AddDrinkManagerViewModel(Model model, ViewHandler viewHandler)
    {
        this.model = model;
        this.viewHandler = viewHandler;
    }

    /**
     * A method that opens the order view
     */
    public boolean onAddDrink(String name,Double price,Integer quantity){
        return this.model.onAddDrink(name, price, quantity);
    }

    /**
     * A method that cancels the add drink
     */
    public void onCancel(){
        viewHandler.openManagerMainPage();
    }
}