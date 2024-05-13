package via.dk.cueandbrew;

import javafx.stage.Stage;
import via.dk.cueandbrew.client.CallbackClient;
import via.dk.cueandbrew.client.CallbackClientImplementation;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.server.ServerInterface;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException, NotBoundException
    {
        Registry registry = LocateRegistry.getRegistry(1099);
        ServerInterface server = (ServerInterface) registry.lookup("cueandbrew");
        CallbackClient client = new CallbackClientImplementation(server);
        Model model = new ModelManager(client);
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory, stage);
        viewHandler.start();
    }

    public static void main(String[] args) {
        launch();
    }
}