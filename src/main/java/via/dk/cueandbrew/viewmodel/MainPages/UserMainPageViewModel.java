package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

public class UserMainPageViewModel
{
  private final Model model;
  private final ViewHandler viewHandler;
  private ObservableList<Reservation> reservations;

  public UserMainPageViewModel(Model model, ViewHandler viewHandler)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.reservations = FXCollections.observableArrayList();
  }

  public void onMakeAReservation()
  {
    this.viewHandler.openCreateReservationView();
  }

  public void openCreateFeedbackPage() {
    this.viewHandler.openCreateFeedbackStage();
  }

  public void onClose()
  {
    this.viewHandler.openStartView();
  }

  public List<Reservation> onSearch(String phone) throws RemoteException
  {
    return this.model.onSearch(phone);
  }
}
