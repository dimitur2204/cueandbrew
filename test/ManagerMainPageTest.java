import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.MainPages.ManagerMainPageViewModel;

import java.rmi.RemoteException;

public class ManagerMainPageTest {
    private Model model;
    private ViewHandler viewHandler;
    private ManagerMainPageViewModel managerMainPageViewModel;

    @BeforeEach
    void setUp() {
        model = Mockito.mock(ModelManager.class);
        viewHandler = Mockito.mock(ViewHandler.class);
        managerMainPageViewModel = new ManagerMainPageViewModel(model, viewHandler);
    }

    @Test
    void testOnCreateReservation() {
        managerMainPageViewModel.onMakeAReservation();
        Mockito.verify(viewHandler).openCreateReservationView();
    }

    @Test
    void testOnCreateFeedback() throws RemoteException {
        Reservation reservation = new Reservation.ReservationBuilder().build();
        Notification notification = new Notification(reservation);
        managerMainPageViewModel.markNotificationAsRead(notification);
        Mockito.verify(model).markNotificationAsRead(notification);
    }
}
