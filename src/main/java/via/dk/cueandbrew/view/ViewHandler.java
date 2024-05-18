package via.dk.cueandbrew.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import via.dk.cueandbrew.view.MainPages.AddDrinkManagerController;
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

/**
 * Class responsible for managing the views of the application. It is responsible for opening and closing the views.
 * It is also responsible for initializing the controllers of the views.
 * @author Andreea Caisim, Darja Jefremova, Dimitar Nizamov, Marius Marcoci
 */
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

    /**
     * Method that returns the ViewModelFactory
     * @return ViewModelFactory
     */
    public ViewModelFactory getViewModelFactory()
    {
        return viewModelFactory;
    }

    /**
     * Method that returns the stage
     * @return Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Method that opens the start view
     */
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

    /** Method that opens the user main page view */
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

    /** Method that opens the add drink view */
    public void openAddDrinkView() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/via/dk/cueandbrew/view/AddDrinkManager.fxml"));
        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        AddDrinkManagerController view = loader.getController();
        view.init(viewModelFactory.getAddDrinkManagerViewModel());
        stage.setTitle("Add drinks");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

/** Method that opens the manager login view */
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

    /** Method that opens the manager main page view */
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

    /** Method that closes the manager main page view */
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

    /** Method that closes the create reservation view */
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

    /** Method that closes the order view */
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
        view.init(viewModelFactory.getFinalizeReservationViewModel(), this);
        finalizeReservationStage.setTitle("Finalize Reservation");

        Scene scene = new Scene(root);
        finalizeReservationStage.setScene(scene);
        finalizeReservationStage.show();
    }

    /** Method that closes the finalize reservation view */
    public void closeFinalizeReservationView() {
        finalizeReservationStage.close();
    }

    /** Method that opens the create feedback view */
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

    /** Method that closes the create feedback view */
    public void closeCreateFeedbackStage() {
        createFeedbackStage.close();
    }

    /** Method that starts the view handler */
    public void start() {
        openStartView();
    }
}
