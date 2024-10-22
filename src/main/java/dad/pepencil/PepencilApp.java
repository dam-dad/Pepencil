package dad.pepencil;

import dad.pepencil.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PepencilApp extends Application {

    public static Stage primaryStage;

    private final RootController rootController = new RootController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        PepencilApp.primaryStage = primaryStage;

        primaryStage.setTitle("Pepencil");
        primaryStage.setScene(new Scene(rootController.getRoot(), 800, 600));
        primaryStage.getIcons().add(new Image("/images/lapiz.png"));
        primaryStage.setOnCloseRequest(this::onCloseRequest);
        primaryStage.show();

    }

    private void onCloseRequest(WindowEvent event) {
        if (!rootController.canClose()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(primaryStage);
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.setTitle("Pepencil");
            alert.setHeaderText("Hay cambios sin guardar");
            alert.setContentText("¿Quiere salir sin guardar los cambios?");
            alert.showAndWait().filter(response -> response == ButtonType.NO).ifPresent(response -> event.consume());
        }
    }

}
