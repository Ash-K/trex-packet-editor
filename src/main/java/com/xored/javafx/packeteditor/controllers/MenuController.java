package com.xored.javafx.packeteditor.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.xored.javafx.packeteditor.data.FieldEditorModel;
import com.xored.javafx.packeteditor.events.ProtocolExpandCollapseEvent;
import com.xored.javafx.packeteditor.metatdata.PacketTemplate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public static final String EXIT_MENU_ITEM = "exit";
    private Logger logger= LoggerFactory.getLogger(MenuController.class);
    
    @Inject
    FieldEditorController controller;

    @Inject
    private EventBus eventBus;

    @FXML
    MenuBar applicationMenu;

    @FXML
    Menu fileMenu;
    
    @FXML
    Menu newTemplateMenu;

    @FXML
    Menu debugMenu;

    @FXML
    Button exitBtn;

    @FXML
    Button binaryModeOnBtn;

    @FXML
    Button abstractModeOnBtn;

    @Inject
    @Named("resources")
    ResourceBundle resourceBundle;

    @Inject
    AppController appController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PacketTemplate.loadTemplates().forEach(templateFile -> {
            MenuItem menuItem = new MenuItem(templateFile.metadata.caption);
            menuItem.setOnAction(event -> controller.getModel().loadTemplate(templateFile));
            newTemplateMenu.getItems().add(menuItem);
        });

        exitBtn.setVisible(true);
        if (System.getenv("DEBUG") != null) {
            debugMenu.setVisible(true);
            binaryModeOnBtn.setVisible(true);
            abstractModeOnBtn.setVisible(true);
        }
    }

    @FXML
    private void handleCloseAction() {
        appController.shutDown();
    }

    @FXML
    public void handleDeleteProtocolAction(ActionEvent actionEvent) {
        getModel().removeLast();
    }

    private FieldEditorModel getModel() { return controller.getModel(); }

    @FXML
    public void handleNewDocument(ActionEvent actionEvent) {
        getModel().newPacket();
    }

    @FXML
    public void handleOpenAction(ActionEvent actionEvent) {
        controller.showLoadDialog();
    }

    @FXML
    public void handleSaveAction(ActionEvent event) {
        controller.showSaveDialog();
    }

    @FXML
    public void handleRecalculateValues(ActionEvent actionEvent) {
        getModel().clearAutoFields();
    }

    @FXML
    public void handleUndo(ActionEvent actionEvent) {
        getModel().undo();
    }

    @FXML
    public void handleRedo(ActionEvent actionEvent){
        getModel().redo();
    }

    @FXML
    public void handleModeBinary(ActionEvent actionEvent) {
        getModel().setBinaryMode(true);
    }

    @FXML
    public void handleModeAbstract(ActionEvent actionEvent) {
        getModel().setBinaryMode(false);
    }

    public void handleExpandAll(ActionEvent actionEvent) {
        eventBus.post(new ProtocolExpandCollapseEvent(true));
    }

    public void handleCollapseAll(ActionEvent actionEvent) {
        eventBus.post(new ProtocolExpandCollapseEvent(false));
    }
}
