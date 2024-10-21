package dad.pepencil;

import dad.pepencil.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PepencilApp extends Application {

    public static Stage primaryStage;

    private final RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        PepencilApp.primaryStage = primaryStage;

        primaryStage.setTitle("Pepencil");
        primaryStage.setScene(new Scene(rootController.getRoot()));
        primaryStage.getIcons().add(new Image("/images/lapiz.png"));
        primaryStage.show();

    }

}
