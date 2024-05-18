package via.dk.cueandbrew.view.MainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Notification;
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
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("notification_created")) {
            Notification notification = (Notification) evt.getNewValue();
            this.notifications.addFirst(notification);
            updateNotifications();
        }
    }

    @FXML
    private VBox notificationsWrapper;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label welcomeLabel;
    private ManagerMainPageViewModel viewModel;
    private ObservableList<Notification> notifications;

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
}
