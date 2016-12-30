package com.anhe3d;

import griffon.core.artifact.GriffonView;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import jssc.SerialPortList;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

@ArtifactProviderFor(GriffonView.class)
@Slf4j
public class GDATO3DView extends AbstractJavaFXGriffonView {

    @MVCMember
    @Nonnull
    private GDATO3DController controller;
    @MVCMember
    @Nonnull
    private GDATO3DModel model;

    @FXML
    private TabPane tabGroup;

    @FXML
    private ChoiceBox<String> serialName;
    @FXML
    private Label serialConnStatus, serialMsg;
    @FXML
    private Button serialConnectActionTarget, serialDisconnectActionTarget, serialDisplayMsgActionTarget, serialCheckBusyActionTarget;
    @FXML
    private Button autoPrintSinglePageActionTarget, testCanvasActionTarget;
    @FXML
    private ToggleButton autoPrintPagesActionTarget;

    @FXML
    private ChoiceBox<Printer> printerName;

    private FileChooser fileChooser;

    @MVCMember
    public void setController(@Nonnull GDATO3DController controller) {
        this.controller = controller;
    }

    @MVCMember
    public void setModel(@Nonnull GDATO3DModel model) {
        this.model = model;
    }

    @Nonnull
    TabPane getTabGroup() {
        return tabGroup;
    }

    @Nonnull
    ChoiceBox<String> getSerialName() {
        return serialName;
    }

    @Nonnull
    ChoiceBox<Printer> getPrinterName() {
        return printerName;
    }

    @Nonnull
    Label getSerialConnStatus() {
        return serialConnStatus;
    }

    @Nonnull
    Label getSerialMsg() {
        return serialMsg;
    }

    @Nonnull
    ToggleButton getAutoPrintPagesActionTarget() {
        return autoPrintPagesActionTarget;
    }

    @Override
    public void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setResizable(false);
        stage.setScene(init());
        getApplication().getWindowManager().attach("mainWindow", stage);

        fileChooser = new FileChooser();
        fileChooser.setTitle(getApplication().getConfiguration().getAsString("application.title", "Open File"));
    }

    // build the UI
    private Scene init() {
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        Node node = loadFromFXML();
        if (node != null) {
            root.getChildren().addAll(node);
            connectActions(node, controller);
        }

        tabGroup.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldTab, newTab) -> model.setMvcIdentifier(newTab != null ? newTab.getId() : null));

        ObservableList<String> allSerialPorts = FXCollections.observableList(Arrays.asList(SerialPortList.getPortNames()));
        serialName.setItems(allSerialPorts);
        serialName.getSelectionModel().selectLast();

        ObservableSet<Printer> printers = Printer.getAllPrinters();
        ObservableList<Printer> allPrinters = FXCollections.observableArrayList();
        allPrinters.addAll(printers);
        printerName.setItems(allPrinters);
        printerName.getSelectionModel().selectFirst();

        testCanvasActionTarget.setText("測試出圖");

//        Action saveAction = actionFor(controller, "fileSave");
//        model.getProjectDocumentModel().addPropertyChangeListener("dirty", (e) -> saveAction.setEnabled((Boolean) e.getNewValue()));
        return scene;
    }

    @Nullable
    File selectFile() {
        Window window = (Window) getApplication().getWindowManager().getStartingWindow();
        return fileChooser.showOpenDialog(window);
    }

    void afterSerialConnected() {
        runInsideUISync(() -> {
            serialConnectActionTarget.setDisable(true);
            serialDisconnectActionTarget.setDisable(false);
            serialDisplayMsgActionTarget.setDisable(false);
        });
    }

    void afterSerialDisconnected() {
        runInsideUISync(() -> {
            serialConnectActionTarget.setDisable(false);
            serialDisconnectActionTarget.setDisable(true);
            serialDisplayMsgActionTarget.setDisable(true);
        });
    }
}
