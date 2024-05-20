package via.dk.cueandbrew.view.MainPages;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.viewmodel.MainPages.UserMainPageViewModel;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is responsible for the UserMainPageController
 * @Author Dimitar Nizamov
 */
public class UserMainPageController
{
  @FXML private Label messageLabel;
  @FXML private TextField phoneLabel;
  @FXML private VBox contentVBox;
  @FXML private Button searchButton;
  @FXML private Label dateLabel;
  @FXML private Label hourLabel;
  private UserMainPageViewModel viewModel;
  private List<Reservation> reservations;

    /**
     * A method that initializes the UserMainPageViewModel
     * @param viewModel The UserMainPageViewModel
     */
  public void init(UserMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
    this.reservations = new ArrayList<>();
    this.searchButton.disableProperty().bind(this.phoneLabel.textProperty().isEmpty());
    this.viewModel.updateDateTime(dateLabel, hourLabel);
    this.viewModel.startDateTimeUpdater(dateLabel, hourLabel);
  }

  /**
   * A method that calls the onMakeAReservation method from the viewModel
   */
  public void onMakeAReservation()
  {
    this.viewModel.onMakeAReservation();
  }

    /** A method that calls the onClose method from the viewModel */
  public void onClose()
  {
    this.viewModel.onClose();
  }

    /**
     * A method that calls the onSearch method from the viewModel
     * @throws RemoteException
     */
  public void onSearch() throws RemoteException
  {
    this.reservations = this.viewModel.onSearch(this.phoneLabel.getText());
    buildReservationsMenu();
  }

    /**
     * A method that builds the reservations menu
     * Setting styles and content for each reservation
     */
  public void buildReservationsMenu() {
    if (!this.reservations.isEmpty()) {
      this.messageLabel.setVisible(false);

      for (Reservation reservation : this.reservations)
      {
        HBox hBox = new HBox();
        hBox.setPrefWidth(250);
        hBox.setSpacing(10);
        hBox.setStyle("-fx-border-style: solid hidden solid hidden; -fx-border-color: black;  -fx-background-radius: 10; -fx-padding: 10;");

        Label content = new Label(
            reservation.getCreationDatetime().toString() + "\n"
                + reservation.getClientFirstName() + " "
                + reservation.getClientLastName() + " has booked the tables "
                + reservation.getBooking().getStringTables() + " on "
                + reservation.getBooking().getDate().toString() + " from "
                + reservation.getBooking().getStartTime() + " until "
                + reservation.getBooking().getEndTime() + "\nPhone number: "
                + reservation.getClientPhoneNumber() + "\nNotes: "
                + (reservation.getNotes().isEmpty() ? "none given" : reservation.getNotes()) + "\nDrinks: " + ((
                reservation.getOrder() != null && !reservation.getOrder().getDrinks().isEmpty()) ?
                reservation.getOrder().getDrinksToString() :
                " none ordered"));
        content.setWrapText(true);
        content.setPrefWidth(250);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button feedbackButton = new Button("Leave Feedback");
        if (reservation.getWasCancelled() == 1 || Timestamp.valueOf(LocalDateTime.parse(this.dateLabel.getText() + "T" + this.hourLabel.getText())).before(Timestamp.valueOf(LocalDateTime.parse(reservation.getBooking().getDate().toString() + "T" + reservation.getBooking().getStartTime().toString())))) {
          feedbackButton.setDisable(true);
        }
        feedbackButton.setOnAction(actionEvent -> this.viewModel.openCreateFeedbackPage());

        Button cancelReservationButton = new Button("Cancel reservation");
        //disable cancel if reservationDate + hour < current date + hour
        if (reservation.getWasCancelled() == 1 || Timestamp.valueOf(LocalDateTime.parse(this.dateLabel.getText() + "T" + this.hourLabel.getText())).after(Timestamp.valueOf(LocalDateTime.parse(reservation.getBooking().getDate().toString() + "T" + reservation.getBooking().getStartTime().toString())))) {
          cancelReservationButton.setDisable(true);
        }
        cancelReservationButton.setOnAction(actionEvent -> {
          try
          {
            if (this.viewModel.cancelReservation(reservation.getReservationId()))
            {
              this.showConfirmationMessage();
              cancelReservationButton.setDisable(true);
            }
            else
            {
              this.showErrorMessage();
            }
          }
          catch (RemoteException e)
          {
            throw new RuntimeException(e);
          }
        });

        VBox buttons = new VBox(feedbackButton, cancelReservationButton);
        hBox.getChildren().addAll(content, spacer, buttons);
        hBox.setAlignment(Pos.CENTER);

        this.contentVBox.getChildren().add(hBox);
      }
    }
    else {
      this.messageLabel.setText("You do not have any reservations made");
      this.messageLabel.setVisible(true);
    }
  }

  private void showConfirmationMessage() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Action successful");
    alert.setHeaderText("Action successful");
    alert.setContentText("Reservation cancelled successfully");
    alert.showAndWait();
  }

  private void showErrorMessage() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Action failed");
    alert.setHeaderText("Action failed");
    alert.setContentText("Could not cancel the reservation");
    alert.showAndWait();
  }
}