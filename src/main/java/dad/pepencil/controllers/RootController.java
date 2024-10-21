package dad.pepencil.controllers;

import dad.pepencil.PepencilApp;
import dad.pepencil.PepencilTab;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    // model

    private final ObjectProperty<Tab> selectedTab = new SimpleObjectProperty<>();

    // view

    @FXML
    private TabPane editionTabPane;

    @FXML
    private VBox emptyPane;

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
        newTab();

        // bindings

        selectedTab.bind(editionTabPane.getSelectionModel().selectedItemProperty());

        // comprobar si no hay pestañas abiertas
        ListProperty<Tab> tabs = new SimpleListProperty<>(editionTabPane.getTabs());
        emptyPane.visibleProperty().bind(Bindings.isEmpty(tabs));

        selectedTab.addListener((o, ov, nv) -> {
            if (nv != null) {
                ((PepencilTab) nv).getController().requestFocus();
            }
        });

    }

    public BorderPane getRoot() {
        return root;
    }

    private EditorController getSelectedEditor() {
        return ((PepencilTab) selectedTab.get()).getController();
    }

    @FXML
    void onCloseAllAction(ActionEvent event) {
        List<Tab> removedTabs = new ArrayList<>();
        editionTabPane
                .getTabs()
                .stream()
                .map(tab -> (PepencilTab) tab)
                .forEach(tab -> {
                    if (tab.getController().close()) {
                        removedTabs.add(tab);
                    } else {
                        event.consume();
                    }
                });
        editionTabPane.getTabs().removeAll(removedTabs);
    }

    @FXML
    void onCloseAction(ActionEvent event) {
        PepencilTab tab = (PepencilTab) selectedTab.get();
        if (tab.getController().close()) {
            editionTabPane.getTabs().remove(tab);
        }
    }

    @FXML
    void onCopyAction(ActionEvent event) {
        getSelectedEditor().copy();
    }

    @FXML
    void onCutAction(ActionEvent event) {
        getSelectedEditor().cut();
    }

    @FXML
    void onExitAction(ActionEvent event) {
        onCloseAllAction(event);
        if (!event.isConsumed()) {
            PepencilApp.primaryStage.close();
        }
    }

    @FXML
    void onNewAction(ActionEvent event) {
        newTab();
    }

    private PepencilTab newTab() {
        PepencilTab newTab = new PepencilTab();
        editionTabPane.getTabs().add(newTab);                               // añadimos la nueva pestaña
        editionTabPane.getSelectionModel().select(newTab);                  // selecciona la pestaña que se acaba de añadir
        return newTab;
    }

    @FXML
    void onOpenAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*"));
        File file = fileChooser.showOpenDialog(getRoot().getScene().getWindow());
        if (file != null) {
            PepencilTab tab = newTab();
            tab.getController().open(file);
        }
    }

    @FXML
    void onPasteAction(ActionEvent event) {
        getSelectedEditor().paste();
    }

    @FXML
    void onRedoAction(ActionEvent event) {
        getSelectedEditor().redo();
    }

    @FXML
    void onSaveAction(ActionEvent event) {
        if (getSelectedEditor().getFile() != null)
            getSelectedEditor().save();
        else
            onSaveAsAction(event);
    }

    @FXML
    void onSaveAsAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*"));
        File file = fileChooser.showSaveDialog(getRoot().getScene().getWindow());
        if (file != null) {
            getSelectedEditor().saveAs(file);
        }
    }

    @FXML
    void onUndoAction(ActionEvent event) {
        getSelectedEditor().undo();
    }

}