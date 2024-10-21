package dad.pepencil;

import dad.pepencil.controllers.EditorController;
import javafx.event.Event;
import javafx.scene.control.Tab;

public class PepencilTab extends Tab {

    private final EditorController controller;

    public PepencilTab() {
        super();
        controller = new EditorController();
        setContent(controller.getRoot());
        textProperty().bind(controller.nameProperty());
        setOnCloseRequest(this::onCloseRequest);
    }

    private void onCloseRequest(Event event) {
        if (!controller.close()) {
            event.consume();
        }
    }

    public EditorController getController() {
        return controller;
    }

}
