package via.dk.cueandbrew.view.MainPages;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.view.NotificationView;
import via.dk.cueandbrew.viewmodel.MainPages.ManagerMainPageViewModel;

public class ManagerMainPageController
{
  @FXML private VBox notificationsWrapper;
  @FXML private Label dateLabel;
  @FXML private Label timeLabel;
  @FXML private Label welcomeLabel;
  private ManagerMainPageViewModel viewModel;
  private ObservableList<Notification> notifications;

  public void init(ManagerMainPageViewModel viewModel)
  {
    this.viewModel = viewModel;
    this.viewModel.updateDateTime(dateLabel, timeLabel);
    this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
    this.viewModel.bindWelcomeLabel(welcomeLabel.textProperty());
    this.notifications = viewModel.getNotifications();
    for(Notification notification : this.notifications) {
        this.notificationsWrapper.getChildren().add(new NotificationView(notification));
      this.notificationsWrapper.getChildren().add(new NotificationView(notification));
      this.notificationsWrapper.getChildren().add(new NotificationView(notification));
      this.notificationsWrapper.getChildren().add(new NotificationView(notification));
      this.notificationsWrapper.getChildren().add(new NotificationView(notification));
    }
  }

  public void onExit() {
    this.viewModel.onExit();
  }

  public void onMakeAReservation() {
    this.viewModel.onMakeAReservation();
  }
}
