package via.dk.cueandbrew.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import via.dk.cueandbrew.view.MainPages.CreateFeedbackController;
import via.dk.cueandbrew.view.MainPages.ManagerMainPageController;
import via.dk.cueandbrew.view.MainPages.UserMainPageController;
import via.dk.cueandbrew.view.Reservation.CreateReservationController;
import via.dk.cueandbrew.view.Reservation.FinalizeReservationController;
import via.dk.cueandbrew.view.Reservation.OrderController;
import via.dk.cueandbrew.view.Start.ManagerLoginController;
import via.dk.cueandbrew.view.Start.StartController;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

import java.io.IOException;

public class ViewHandler {
    private final ViewModelFactory viewModelFactory;
    private final Stage stage;
    private final Stage finalizeReservationStage;
    private final Stage createFeedbackStage;

    public ViewHandler(ViewModelFactory viewModelFactory, Stage stage) {
        this.viewModelFactory = viewModelFactory;
        this.viewModelFactory.instantiateViewModels(this);
        this.stage = stage;
        this.finalizeReservationStage = new Stage();
        this.finalizeReservationStage.initModality(Modality.APPLICATION_MODAL);
        this.finalizeReservationStage.initOwner(this.stage);
        this.createFeedbackStage = new Stage();
        this.createFeedbackStage.initModality(Modality.APPLICATION_MODAL);
        this.createFeedbackStage.initOwner(this.stage);
    }

    public ViewModelFactory getViewModelFactory()
    {
        return viewModelFactory;
    }

    public Stage getStage() {
        return stage;
    }

    public void openStartView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/Start.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        StartController view = loader.getController();
        view.init(viewModelFactory.getStartViewModel());
        stage.setTitle("Home");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openUserMainPageView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/UserMainPage.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        UserMainPageController view = loader.getController();
        view.init(viewModelFactory.getUserMainPageViewModel());
        stage.setTitle("Main");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openManagerLoginView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/ManagerLogin.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        ManagerLoginController view = loader.getController();
        view.init(viewModelFactory.getManagerLoginViewModel());
        stage.setTitle("Log in");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openManagerMainPage()
    {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/ManagerMainPage.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        ManagerMainPageController view = loader.getController();
        view.init(viewModelFactory.getManagerMainPageViewModel());
        stage.setTitle("Main");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openCreateReservationView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/CreateReservationView.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        CreateReservationController view = loader.getController();
        view.init(viewModelFactory.getCreateReservationViewModel());
        stage.setTitle("Create Reservation");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openOrder() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/Order.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        OrderController view = loader.getController();
        view.init(viewModelFactory.getOrderViewModel());
        stage.setTitle("Add Drinks");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openFinalizeReservationView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/FinalizeReservation.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        FinalizeReservationController view = loader.getController();
        view.init(viewModelFactory.getFinalizeReservationViewModel());
        finalizeReservationStage.setTitle("Finalize Reservation");

        Scene scene = new Scene(root);
        finalizeReservationStage.setScene(scene);
        finalizeReservationStage.show();
    }

    public void closeFinalizeReservationView() {
        finalizeReservationStage.close();
    }

    public void openCreateFeedbackStage() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/CreateFeedback.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        CreateFeedbackController view = loader.getController();
        view.init(viewModelFactory.getCreateFeedbackViewModel());
        createFeedbackStage.setTitle("Create Feedback");

        Scene scene = new Scene(root);
        createFeedbackStage.setScene(scene);
        createFeedbackStage.show();
    }

    public void closeCreateFeedbackStage() {
        createFeedbackStage.close();
    }

    public void start() {
        openStartView();
    }
}
