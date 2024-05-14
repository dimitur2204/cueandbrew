package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.rmi.RemoteException;

public class UserMainPageViewModel
{
  private Model model;
  private final ViewHandler viewHandler;
  private ObservableList<Reservation> reservations;

  public UserMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    reservations = FXCollections.observableArrayList();
    var res = new Reservation.ReservationBuilder()
            .setClientFirstName("Lol")
            .setClientLastName("Lol")
            .setClientPhoneNumber("Lol")
            .setNotes("Lol").build();
    reservations.add(res);
  }

  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

  public void onClose()
  {
    this.viewHandler.openStartView();
  }

  public void onSearch(String phone) throws RemoteException
  {
    this.model.onSearch(phone);
  }
}
