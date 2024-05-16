package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class UserMainPageController
{
  @FXML private Label messageLabel;
  @FXML private TextField phoneLabel;
  @FXML private VBox contentVBox;
  @FXML private Button searchButton;
  private UserMainPageViewModel viewModel;
  private List<Reservation> reservations;

  public void init(UserMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
    this.reservations = new ArrayList<>();
    this.searchButton.disableProperty().bind(this.phoneLabel.textProperty().isEmpty());
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
    this.reservations = this.viewModel.onSearch(this.phoneLabel.getText());
    buildReservationsMenu();
  }

  public void buildReservationsMenu() {
    if (!this.reservations.isEmpty()) {
      this.messageLabel.setVisible(false);

      for (Reservation reservation : this.reservations)
      {
        HBox hBox = new HBox();
        Label content = new Label(
            reservation.getCreationDatetime().toString() + "\n"
                + reservation.getClientFirstName() + " "
                + reservation.getClientLastName() + " has booked the tables "
                + reservation.getBooking().getStringTables() + " on "
                + reservation.getBooking().getDate().toString() + " from "
                + reservation.getBooking().getStartTime() + " until "
                + reservation.getBooking().getEndTime() + "\nPhone number: "
                + reservation.getClientPhoneNumber() + "\nNotes: "
                + reservation.getNotes() + "\nDrinks: " + ((
                reservation.getOrder() != null) ?
                reservation.getOrder().getDrinksToString() :
                " none ordered"));
        content.setWrapText(true);

        HBox margin = new HBox();
        margin.setPrefWidth(100);

        Button button = new Button("Leave Feedback");
        button.setOnAction(actionEvent -> this.viewModel.openCreateFeedbackPage());

        hBox.getChildren().addAll(content, margin, button);
        hBox.setAlignment(Pos.CENTER);

        this.contentVBox.getChildren().add(hBox);
      }
    }
    else {
      this.messageLabel.setText("You do not have any reservations made");
      this.messageLabel.setVisible(true);
    }
  }
}
