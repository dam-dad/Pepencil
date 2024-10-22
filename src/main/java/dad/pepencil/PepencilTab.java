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
        setOnCloseRequest(this::onCloseRequest); // captura el evento de cierre de la pestaña
    }

    private void onCloseRequest(Event event) {
        // si no se puede cerrar, consumimos el evento (así no se cerrará la pestaña)
        if (!controller.close()) {
            event.consume();
        }
    }

    public EditorController getController() {
        return controller;
    }

}
