package dad.pepencil.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // model

    private ObjectProperty<Tab> selectedTab = new SimpleObjectProperty<>();

    // logic

    private final MapProperty<Tab, EditorController> controllers = new SimpleMapProperty<>(FXCollections.observableHashMap());

    // view

    @FXML
    private TabPane editionTabPane;

    @FXML
    private BorderPane root;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // elimino las pestañas cargadas por defecto
        editionTabPane.getTabs().clear();

        // inicializamos el editor con un fichero nuevo
        newFile();

        // bindings

        selectedTab.bind(editionTabPane.getSelectionModel().selectedItemProperty());

    }

    public BorderPane getRoot() {
        return root;
    }

    @FXML
    void onClaseAllAction(ActionEvent event) {

    }

    @FXML
    void onCloseAction(ActionEvent event) {

    }

    @FXML
    void onCopyAction(ActionEvent event) {
        controllers.get(selectedTab.get()).copy();
    }

    @FXML
    void onCutAction(ActionEvent event) {

//        Tab selectedTab = editionTabPane.getSelectionModel().getSelectedItem();
//        EditorController controller = controllers.get(selectedTab);
//        controller.cut();

        controllers.get(selectedTab.get()).cut();

    }

    @FXML
    void onExitAction(ActionEvent event) {

    }

    @FXML
    void onNewAction(ActionEvent event) {
        newFile();
    }

    private EditorController newFile() {

        EditorController editorController = new EditorController();         // creamos el controlador para editar el nuevo fichero

        Tab newTab = new Tab();                                             // creamos una nueva pestaña para un fichero nuevo
        newTab.setContent(editorController.getRoot());
        newTab.textProperty().bind(editorController.nameProperty());

        editionTabPane.getTabs().add(newTab);                               // añadimos la nueva pestaña
        editionTabPane.getSelectionModel().select(newTab);                  // selecciona la pestaña que se acaba de añadir

        controllers.put(newTab, editorController);                          // vinculamos en el mapa la pestaña con su controlador

        return editorController;
    }

    @FXML
    void onOpenAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*"));
        File file = fileChooser.showOpenDialog(getRoot().getScene().getWindow());
        if (file != null) {

            EditorController controller = newFile();
            controller.setFile(file);

        }

    }

    @FXML
    void onPasteAction(ActionEvent event) {
        controllers.get(selectedTab.get()).paste();
    }

    @FXML
    void onRedoAction(ActionEvent event) {
        controllers.get(selectedTab.get()).redo();
    }

    @FXML
    void onSaveAction(ActionEvent event) {

    }

    @FXML
    void onSaveAsAction(ActionEvent event) {

    }

    @FXML
    void onUndoAction(ActionEvent event) {
        controllers.get(selectedTab.get()).undo();
    }
}
