package via.dk.cueandbrew.viewmodel.MainPages;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

public class AddDrinkManagerViewModel
{
    private Model model;
    private ViewHandler viewHandler;
    private OrderViewModel orderViewModel;
    public AddDrinkManagerViewModel(Model model, ViewHandler viewHandler)
    {
        this.model = model;
        this.viewHandler = viewHandler;
        this.orderViewModel=new OrderViewModel(model,viewHandler);
    }

    public void onAddDrink(String name,Double price,Integer quantity){
        //TODO: save to db
        viewHandler.openOrder();
    }

    public void onCancel(){
        viewHandler.openManagerMainPage();
    }
}