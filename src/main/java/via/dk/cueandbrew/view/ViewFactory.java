package via.dk.cueandbrew.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import via.dk.cueandbrew.Application;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory {
    public static final String CREATE_RESERVATION = "createreservation";

    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private CreateReservationController createReservationController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.createReservationController = null;
    }

    public Region loadCreateReservationView() {
        if (createReservationController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CreateReservationView.fxml"));
            try {
                Region root = loader.load();
                createReservationController = loader.getController();
                createReservationController.init(root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        return createReservationController.getRoot();
    }

    public Region load(String id) {
        return switch (id) {
            case CREATE_RESERVATION -> loadCreateReservationView();
            default -> throw new IllegalArgumentException("Unknown cueandbrew: " + id);
        };
    }
}
