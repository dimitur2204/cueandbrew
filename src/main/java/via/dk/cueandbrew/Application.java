package via.dk.cueandbrew;

import javafx.stage.Stage;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model model = new ModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory, stage);
        viewHandler.start();
    }

    public static void main(String[] args) {
        launch();
    }
}