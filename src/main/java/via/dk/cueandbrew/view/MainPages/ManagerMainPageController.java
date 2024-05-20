package via.dk.cueandbrew.view.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Feedback;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Registration;
import via.dk.cueandbrew.view.NotificationView;
import via.dk.cueandbrew.viewmodel.MainPages.ManagerMainPageViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

/**
 * A class that is responsible for the ManagerMainPageController
 * @author Dimitar Nizamov
 */
public class ManagerMainPageController implements PropertyChangeListener {
    @FXML private VBox notificationsWrapper;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label welcomeLabel;
    @FXML VBox feedbacksWrapper;
    private ManagerMainPageViewModel viewModel;
    private ObservableList<Notification> notifications;
    private ObservableList<Feedback> feedbacks;

    /**
     * A method that initializes the ManagerMainPageViewModel
     * @param viewModel The ManagerMainPageViewModel
     */
    public void init(ManagerMainPageViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.bindWelcomeLabel(welcomeLabel.textProperty());
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
        this.viewModel.addPropertyChangeListener(this);
        try {
            this.notifications = FXCollections.observableArrayList(this.viewModel.fetchNotifications());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        updateNotifications();
        this.feedbacks = FXCollections.observableArrayList(this.viewModel.fetchFeedbacks());
        updateFeedbacks();
    }

    private void updateFeedbacks()
    {
      feedbacksWrapper.getChildren().clear();
      for (Feedback feedback : this.feedbacks)
      {
        HBox firstRow = new HBox();
        firstRow.setPrefWidth(250);
        firstRow.setSpacing(10);

        Label author = new Label(feedback.getAuthor_firstname() + " "
            + feedback.getAuthor_lastname());
        Label type = new Label(feedback.getType());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        firstRow.getChildren().addAll(author, type);

        HBox secondRow = new HBox();
        secondRow.setPrefWidth(250);
        secondRow.setSpacing(10);

        Label content = new Label(feedback.getContent());
        content.setWrapText(true);

        Button checked = new Button("Check");
        checked.setOnAction(event -> {
            if (this.viewModel.checkFeedback(Registration.getInstance().getManager_id(), feedback.getFeedback_id())) {
              feedbacks.remove(feedback);
              updateFeedbacks();
            }
        });
        checked.setDisable(feedback.getChecked_by_id() != 0);

        secondRow.getChildren().addAll(content, checked);

        VBox feedbackBox = new VBox(firstRow, secondRow);
        feedbackBox.setPrefWidth(250);
        feedbackBox.setSpacing(10);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.feedbacksWrapper.getChildren().add(feedbackBox);
      }
    }

    /**
     * A method that updates the notifications
     */
    private void updateNotifications() {
        notificationsWrapper.getChildren().clear();
        for (Notification notification : this.notifications) {
            NotificationView notificationView = new NotificationView(notification, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewModel.markNotificationAsRead(notification);
                    notifications.remove(notification);
                    updateNotifications();
                }
            });
            notificationsWrapper.getChildren().add(notificationView);
        }
    }

    /**
     * A method that calls the onExit method from the viewModel
     */
    public void onExit() {
        this.viewModel.onExit();
    }

    /**
     * A method that calls the onAddDrink method from the viewModel
     */
    public void onAddDrink() {
        this.viewModel.onOpenAddDrink();
    }

    /**
     * A method that calls the onAddTable method from the viewModel
     */
    public void onMakeAReservation() {
        this.viewModel.onMakeAReservation();
    }
    public void onAddADrink(){
        this.viewModel.onAddADrink();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("notification_created")) {
            Notification notification = (Notification) evt.getNewValue();
            this.notifications.addFirst(notification);
            updateNotifications();
        }
        else if (evt.getPropertyName().equals("feedback_created")) {
            this.feedbacks.addFirst((Feedback) evt.getNewValue());
            updateFeedbacks();
        }
        else if (evt.getPropertyName().equals("check_feedback"))
        {
          updateFeedbacks();
        }
    }
}
