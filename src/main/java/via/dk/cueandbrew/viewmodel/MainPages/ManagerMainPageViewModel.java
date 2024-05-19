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

public class ManagerMainPageViewModel implements PropertyChangeListener {
    private final PropertyChangeSupport support;
    private final Model model;
    private final ViewHandler viewHandler;
    private final StringProperty welcomeLabel;

    public ManagerMainPageViewModel(Model model, ViewHandler viewHandler) {
        this.support = new PropertyChangeSupport(this);
        this.model = model;
        this.welcomeLabel = new SimpleStringProperty();
        this.model.addPropertyChangeListener(this);
        this.viewHandler = viewHandler;
    }
    public List<Notification> fetchNotifications() throws RemoteException {
        //return only notificaitnos that are not seen
        return this.model.fetchNotifications().stream().filter(notification -> notification.getWasSeen() == 0).toList();
    }

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
    public void bindWelcomeLabel(StringProperty property) {
        property.bind(welcomeLabel);
    }

    public void updateDateTime(Label date, Label time) {
        this.model.updateDateTime(date, time);
    }

    public void startDateTimeUpdater(Label date, Label time) {
        this.model.startDateTimeUpdater(date, time);
    }

    public void onMakeAReservation() {
        this.viewHandler.openCreateReservationView();
    }

    public void onOpenAddDrink() {
        this.viewHandler.openAddDrinkView();
    }

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

//  public void fireCreateFeedbackPropertyChange(int feedback_id, String content, String selectedType, String firstname, String lastname) {
//      Feedback feedback = new Feedback(feedback_id, firstname, lastname, content, selectedType, 0);
//      this.support.firePropertyChange("feedback_created", null, feedback);
//    System.out.println(feedback);
//  }

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
