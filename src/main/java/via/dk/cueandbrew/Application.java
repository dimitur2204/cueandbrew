package via.dk.cueandbrew;

import javafx.stage.Stage;
import via.dk.cueandbrew.model.CreateReservationModel;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        CreateReservationModel createReservationModel = new CreateReservationModel();
        ViewModelFactory viewModelFactory = new ViewModelFactory(createReservationModel);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}