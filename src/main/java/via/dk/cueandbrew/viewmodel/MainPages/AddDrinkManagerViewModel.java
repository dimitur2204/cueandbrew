package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

/**
 * A class that is responsible for the AddDrinkManagerViewModel
 * @author Andreea Caisim
 */
public class AddDrinkManagerViewModel
{
    private Model model;
    private ViewHandler viewHandler;
    private OrderViewModel orderViewModel;
    /** A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
    public AddDrinkManagerViewModel(Model model, ViewHandler viewHandler)
    {
        this.model = model;
        this.viewHandler = viewHandler;
        this.orderViewModel=new OrderViewModel(model,viewHandler);
    }

    /**
     * A method that opens the order view
     */
    public void onAddDrink(String name,Double price,Integer quantity){
        //TODO: save to db
        viewHandler.openOrder();
    }

    /**
     * A method that cancels the add drink
     */
    public void onCancel(){
        viewHandler.openManagerMainPage();
    }
}