package via.dk.cueandbrew.view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import via.dk.cueandbrew.viewmodel.ViewModelFactory;

public class ViewHandler {

    private final Scene currentScene;
    private Stage primaryStage;
    private final ViewFactory viewFactory;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewFactory = new ViewFactory(this, viewModelFactory);
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView(ViewFactory.CREATE_RESERVATION);
    }

    public void openView(String id) {
        Region root = viewFactory.load(id);
        currentScene.setRoot(root);
        if (root.getUserData() == null) {
            primaryStage.setTitle("Create Reservation");
        } else {
            primaryStage.setTitle(root.getUserData().toString());
        }
        primaryStage.setScene(currentScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void closeView() {
        primaryStage.close();
    }
}
