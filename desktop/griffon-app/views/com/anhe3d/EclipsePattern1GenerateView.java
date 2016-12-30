package com.anhe3d;

import griffon.core.artifact.GriffonView;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;
import javax.inject.Inject;

@ArtifactProviderFor(GriffonView.class)
public class EclipsePattern1GenerateView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    private GDATO3DView parentView;

    @MVCMember
    @Nonnull
    private EclipsePattern1GenerateController controller;

    @MVCMember
    @Nonnull
    private EclipsePattern1GenerateModel model;

    @MVCMember
    @Nonnull
    private String tabName;

    private Tab tab;

    @FXML
    @Getter
    private Group canvasGroup;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label labelX, labelY, labelR, labelD1, labelD2;

    @FXML
    private TextField inputXActionTarget, inputYActionTarget, inputRActionTarget, inputInitialEActionTarget, inputD1ActionTarget, inputD2ActionTarget;

    @FXML
    private Button refreshActionTarget, printActionTarget;

    @Inject
    private SerialService serialService;

    @Inject
    private PrintService printService;

    @MVCMember
    public void setController(@Nonnull EclipsePattern1GenerateController controller) {
        this.controller = controller;
    }

    @MVCMember
    public void setModel(@Nonnull EclipsePattern1GenerateModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());

        Node node = loadFromFXML();
        tab.setContent(node);
        tab.setClosable(true);

        parentView.getTabGroup().getTabs().add(tab);

        labelX.setText(getApplication().getMessageSource().getMessage("EclipsePattern1Generator.action.LabelX.text"));
        labelY.setText(getApplication().getMessageSource().getMessage("EclipsePattern1Generator.action.LabelY.text"));
        labelR.setText(getApplication().getMessageSource().getMessage("EclipsePattern1Generator.action.LabelR.text"));
        labelD1.setText(getApplication().getMessageSource().getMessage("EclipsePattern1Generator.action.LabelD1.text"));
        labelD2.setText(getApplication().getMessageSource().getMessage("EclipsePattern1Generator.action.LabelD2.text"));

        colorPicker.setOnAction(e -> model.setColor(colorPicker.getValue()));

        inputXActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNotBlank(newValue)) {
                model.setX(Double.parseDouble(newValue));
            }
        });
        inputYActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setY(Double.parseDouble(newValue));
            }
        });
        inputRActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setR(Double.parseDouble(newValue));
            }
        });
        inputInitialEActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setInitialE(Double.parseDouble(newValue));
            }
        });
        inputD1ActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setD1(Double.parseDouble(newValue));
            }
        });
        inputD2ActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setD2(Double.parseDouble(newValue));
            }
        });

        final Canvas canvas = new Canvas(400, 400);
        canvas.setCache(true);
        canvasGroup.getChildren().add(canvas);

//        printActionTarget.setOnAction((ActionEvent event) -> {
//            Node n = canvasGroup.getChildren().get(0);
////            WritableImage wim = new WritableImage(500, 500);
////            n.snapshot(null, wim);
////            ImageView iv = new ImageView();
////            iv.setImageView(wim);
//            printService.print(n);
//
//            serialService.write(CommProtocol.E_GO_UP_STEP.code() + " " + 500);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + " " + 300);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            serialService.write(CommProtocol.POWDER_START);
//        });

        connectActions(node, controller);
    }

    @Override
    public void mvcGroupDestroy() {
        runInsideUISync(() -> parentView.getTabGroup().getTabs().remove(tab));
    }
}
