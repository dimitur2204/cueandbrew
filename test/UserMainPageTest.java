import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;

import java.rmi.RemoteException;
import java.util.List;

public class UserMainPageTest {
    private Model model;
    private UserMainPageViewModel userMainPageViewModel;
    private ViewHandler viewHandler;
    @BeforeEach
    void setUp() {
        model = Mockito.mock(ModelManager.class);
        viewHandler = Mockito.mock(ViewHandler.class);
        userMainPageViewModel = new UserMainPageViewModel(model, viewHandler);
    }

    @Test
    void testOnMakeReservation() {
        userMainPageViewModel.onMakeAReservation();
        Mockito.verify(viewHandler).openCreateReservationView();
    }

    @Test
    void testOnSearch() throws RemoteException {
        Reservation.ReservationBuilder reservationBuilder = new Reservation.ReservationBuilder();
        Mockito.when(model.onSearch("123")).thenReturn(List.of(reservationBuilder.build()));
        userMainPageViewModel.onSearch("123");
        Mockito.verify(model).onSearch("123");
    }
}
