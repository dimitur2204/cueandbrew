package via.dk.cueandbrew.view.MainPages;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;

import java.rmi.RemoteException;
import java.util.List;

public class UserMainPageController
{
  @FXML public Label messageLabel;
  @FXML public TextField phoneLabel;
  private UserMainPageViewModel viewModel;

  public void init(UserMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
  }

  public void onMakeAReservation()
  {
    this.viewModel.onMakeAReservation();
  }

  public void onClose()
  {
    this.viewModel.onClose();
  }

  public void onSearch() throws RemoteException
  {
    this.viewModel.onSearch(this.phoneLabel.getText());
  }
}
