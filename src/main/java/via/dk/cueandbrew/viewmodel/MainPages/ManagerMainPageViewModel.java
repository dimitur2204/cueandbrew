package via.dk.cueandbrew.viewmodel.MainPages;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;
import via.dk.cueandbrew.shared.*;
import via.dk.cueandbrew.view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.List;

/**
 * A class that is responsible for the ManagerMainPageViewModel
 * @Author Dimitar Nizamov, Andreea Caisim
 */
public class ManagerMainPageViewModel implements PropertyChangeListener {
    private final PropertyChangeSupport support;
    private final Model model;
    private final ViewHandler viewHandler;
    private final StringProperty welcomeLabel;

    /**
     * A constructor that sets the model and the viewHandler
     * @param model The model
     * @param viewHandler The viewHandler
     */
    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.support = new PropertyChangeSupport(this);
        this.model = model;
        this.welcomeLabel = new SimpleStringProperty();
        this.model.addPropertyChangeListener(this);
        this.viewHandler = viewHandler;
    }

    /**
     * A method that fetches the notifications
     * @return The list of notifications
     * @throws RemoteException If the method failed
     */
    public List<Notification> fetchNotifications() throws RemoteException {
        //return only notificaitnos that are not seen
        return this.model.fetchNotifications().stream().filter(notification -> notification.getWasSeen() == 0).toList();
    }

    /**
     * A method that marks the notification as read
     * @param notification The notification
     */
    public void markNotificationAsRead(Notification notification) {
        try {
            this.model.markNotificationAsRead(notification);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
  @FXML
  public void onAddADrink()
  {
    this.viewHandler.openAddDrinkManager();

  }

    /**
     * A method that binds the welcome label
     * @param property The property
     */
    public void bindWelcomeLabel(StringProperty property) {
        property.bind(welcomeLabel);
    }

    /**
     * A method that updates the date and time
     * @param date The date
     * @param time The time
     */
    public void updateDateTime(Label date, Label time) {
        this.model.updateDateTime(date, time);
    }

    /**
     * A method that starts the date time updater
     * @param date The date
     * @param time The time
     */
    public void startDateTimeUpdater(Label date, Label time) {
        this.model.startDateTimeUpdater(date, time);
    }

    /**
     * A method that opens the finalize reservation view
     */
    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    /**
     * A method that opens the create feedback view
     */
    public void onOpenAddDrink() {
        this.viewHandler.openAddDrinkView();
    }

    /**
     * A method that invalidates the login and opens the manager login view
     */
    public void onExit() {
        Registration temp = Registration.getInstance();
        temp.setManager_id(-1);
        temp.setLogin("");
        temp.setId(null);
        this.welcomeLabel.setValue("");
        this.viewHandler.getViewModelFactory().getManagerLoginViewModel().clearErrorLabel();
        this.viewHandler.openManagerLoginView();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

  public List<Feedback> fetchFeedbacks()
  {
    return this.model.fetchFeedbacks();
  }

  public boolean checkFeedback(int managerId, int feedbackId)
  {
    return this.model.checkFeedback(managerId, feedbackId);
  }

    /**
     * A method that listens for property changes
     * @param evt The event
     */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("welcome")) {
      this.welcomeLabel.setValue("Welcome " + evt.getNewValue().toString());
    }
    else if (evt.getPropertyName().equals("reservation_created")) {
      Reservation reservation = (Reservation) evt.getNewValue();
      Notification notification = new Notification(reservation);
      try {
        this.model.createNotification(notification);
        this.support.firePropertyChange("notification_created", null, notification);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
    else if (evt.getPropertyName().equals("created_feedback"))
    {
      this.support.firePropertyChange("feedback_created", null, evt.getNewValue());
    }
    else if (evt.getPropertyName().equals("check_feedback"))
    {
      this.support.firePropertyChange("check_feedback", null, evt.getNewValue());
    }
  }
}
