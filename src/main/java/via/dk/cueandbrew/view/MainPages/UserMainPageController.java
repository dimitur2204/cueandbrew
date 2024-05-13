package via.dk.cueandbrew.view.MainPages;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;

import java.util.List;

public class UserMainPageController
{
  public HBox reservationsContainer;
  private UserMainPageViewModel viewModel;

  public void init(UserMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
    List<Reservation> reservations = this.viewModel.getReservations();
    for (Reservation reservation : reservations)
    {
      Label names = new Label();
      names.setText(reservation.getClientFirstName()+" "
          + reservation.getClientLastName()+" reserved a table \n");
      Label notes = new Label();
      notes.setText(",notes:"+reservation.getNotes());
      reservationsContainer.getChildren().add(names);
      reservationsContainer.getChildren().add(notes);
    }

  }

  public void onMakeAReservation()
  {
    this.viewModel.onMakeAReservation();
  }

  public void onClose()
  {
    this.viewModel.onClose();
  }
}
