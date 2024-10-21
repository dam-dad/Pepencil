package dad.pepencil;

import dad.pepencil.controllers.EditorController;
import javafx.scene.control.Tab;

public class PepencilTab extends Tab {

    private final EditorController controller;

    public PepencilTab() {
        super();
        controller = new EditorController();
        setContent(controller.getRoot());
        textProperty().bind(controller.nameProperty());
    }

    public EditorController getController() {
        return controller;
    }

}
