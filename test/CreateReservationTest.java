import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.shared.*;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Reservation.CreateReservationViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.FinalizeReservationViewModel;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateReservationTest {
    private Model model;
    private CreateReservationViewModel createViewModel;
    private OrderViewModel orderViewModel;
    private FinalizeReservationViewModel finalizeReservationViewModel;
    private ViewHandler viewHandler;

    @BeforeEach
    void setUp() {
        model = Mockito.mock(ModelManager.class);
        viewHandler = Mockito.mock(ViewHandler.class);
        createViewModel = new CreateReservationViewModel(model, viewHandler);
        orderViewModel = new OrderViewModel(model, viewHandler);
        finalizeReservationViewModel = new FinalizeReservationViewModel(model, viewHandler);
    }

    private Booking getMockBooking() {
        Booking booking = new Booking();
        booking.setDate(java.sql.Date.valueOf("2002-04-22"));
        booking.setStartTime(java.sql.Time.valueOf("12:00:00"));
        booking.setEndTime(java.sql.Time.valueOf("12:30:00"));
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        ArrayList<Table> tables = new ArrayList<>();
        tables.add(table1);
        tables.add(table2);
        booking.setTables(tables);
        return booking;
    }
    private Order getMockOrder() {
        Order order = new Order();
        ArrayList<Drink> drinks = new ArrayList<>();
        Drink drink1 = new Drink(1, "Beer", 20, 500);
        Drink drink2 = new Drink(2, "Wine", 30, 100);
        drinks.add(drink1);
        drinks.add(drink2);
        order.setDrinks(drinks);
        order.setExpectedDatetime(java.sql.Timestamp.valueOf("2002-04-22 12:00:00"));
        return order;
    }

    @Test
    void testBookingStep() {
        var reservationBuilder = new Reservation.ReservationBuilder();
        Mockito.when(model.getReservationBuilder()).thenReturn(reservationBuilder);
        var dateTime = LocalDateTime.of(2002, 4, 22, 12, 0);
        createViewModel.chooseDateTime(dateTime);
        createViewModel.chooseDuration(30);
        createViewModel.chooseTable(1);
        createViewModel.chooseTable(2);
        createViewModel.onNext();
        Assertions.assertEquals(reservationBuilder.build().getBooking(), getMockBooking());
    }

    @Test
    void testOrderStep() {
        var reservationBuilder = new Reservation.ReservationBuilder();
        Mockito.when(model.getReservationBuilder()).thenReturn(reservationBuilder);
        Drink drink = new Drink(1, "Beer", 20, 500);
        Drink drink2 = new Drink(2, "Wine", 30, 100);
        orderViewModel.addDrink(drink);
        orderViewModel.addDrink(drink2);
        var ts = java.sql.Timestamp.valueOf("2002-04-22 12:00:00");
        orderViewModel.onConfirm(ts);
        Assertions.assertEquals(reservationBuilder.build().getOrder(), getMockOrder());
    }

    @Test
    void testFinalizeStep() {
        var reservationBuilder = new Reservation.ReservationBuilder();
        Mockito.when(model.getReservationBuilder()).thenReturn(reservationBuilder);
        finalizeReservationViewModel.onFinalize("John", "Doe", "12345678", "");
        Assertions.assertEquals(reservationBuilder.build().getClientFirstName(), "John");
        Assertions.assertEquals(reservationBuilder.build().getClientLastName(), "Doe");
        Assertions.assertEquals(reservationBuilder.build().getClientPhoneNumber(), "12345678");
    }
}
