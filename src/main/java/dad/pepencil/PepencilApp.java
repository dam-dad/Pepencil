package dad.pepencil;

import dad.pepencil.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PepencilApp extends Application {

    private RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Pepencil");
        primaryStage.setScene(new Scene(rootController.getRoot()));
        primaryStage.show();

    }

}
