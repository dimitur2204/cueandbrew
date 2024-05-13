package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

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
    var res = new Reservation.ReservationBuilder("Allan", "Acbs",
        "24524252").setNotes("Lol").build();
    reservations.add(res);
  }

  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

  public int size()
  {
    return reservations.size();
  }

  public ObservableList<Reservation> getReservations()
  {
    return reservations;
  }


  public void onClose()
  {
    this.viewHandler.openStartView();
  }
}
