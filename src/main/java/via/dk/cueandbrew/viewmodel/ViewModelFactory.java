package via.dk.cueandbrew.viewmodel;

import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.MainPages.AddDrinkManagerViewModel;
import via.dk.cueandbrew.viewmodel.MainPages.CreateFeedbackViewModel;
import via.dk.cueandbrew.viewmodel.MainPages.ManagerMainPageViewModel;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.CreateReservationViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;
import via.dk.cueandbrew.viewmodel.Start.StartViewModel;

/**
 * Factory class for instantiating all view models
 *  @author Andreea Caisim, Darja Jefremova, Dimitar Nizamov, Marius Marcoci
 */
public class ViewModelFactory {
    private final Model model;
    private StartViewModel startViewModel;
    private UserMainPageViewModel userMainPageViewModel;
    private ManagerLoginViewModel managerLoginViewModel;
    private ManagerMainPageViewModel managerMainPageViewModel;
    private CreateReservationViewModel createReservationViewModel;
    private AddDrinkManagerViewModel addDrinkManagerViewModel;
    private OrderViewModel orderViewModel;
    private FinalizeReservationViewModel finalizeReservationViewModel;
    private CreateFeedbackViewModel createFeedbackViewModel;

    public ViewModelFactory(Model model) {
        this.model = model;
    }

    public void instantiateViewModels(ViewHandler viewHandler) {
        this.startViewModel = new StartViewModel(model, viewHandler);
        this.userMainPageViewModel = new UserMainPageViewModel(model, viewHandler);
        this.managerLoginViewModel = new ManagerLoginViewModel(model, viewHandler);
        this.managerMainPageViewModel = new ManagerMainPageViewModel(model, viewHandler);
        this.createReservationViewModel = new CreateReservationViewModel(model, viewHandler);
        this.orderViewModel = new OrderViewModel(model, viewHandler);
        this.finalizeReservationViewModel = new FinalizeReservationViewModel(model, viewHandler);
        this.createFeedbackViewModel = new CreateFeedbackViewModel(model, viewHandler);
        this.addDrinkManagerViewModel=new AddDrinkManagerViewModel(model,viewHandler);
    }

    public StartViewModel getStartViewModel()
    {
        return startViewModel;
    }

    public UserMainPageViewModel getUserMainPageViewModel()
    {
        return userMainPageViewModel;
    }

    public ManagerLoginViewModel getManagerLoginViewModel()
    {
        return managerLoginViewModel;
    }

    public AddDrinkManagerViewModel getAddDrinkManagerViewModel(){
        return addDrinkManagerViewModel;
    }
    public ManagerMainPageViewModel getManagerMainPageViewModel()
    {
        return managerMainPageViewModel;
    }

    public CreateReservationViewModel getCreateReservationViewModel()
    {
        return createReservationViewModel;
    }

    public OrderViewModel getOrderViewModel()
    {
        return orderViewModel;
    }

    public FinalizeReservationViewModel getFinalizeReservationViewModel()
    {
        return finalizeReservationViewModel;
    }

    public CreateFeedbackViewModel getCreateFeedbackViewModel()
    {
        return createFeedbackViewModel;
    }
}
