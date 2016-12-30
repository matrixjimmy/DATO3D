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
public class PillPatternGenerateView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    private GDATO3DView parentView;

    @MVCMember
    @Nonnull
    private PillPatternGenerateController controller;

    @MVCMember
    @Nonnull
    private PillPatternGenerateModel model;

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
    private Label labelX, labelY, labelR, labelH, labelD1, labelD2;

    @FXML
    private TextField inputXActionTarget, inputYActionTarget, inputRActionTarget, inputHActionTarget, inputD1ActionTarget, inputD2ActionTarget;

    @FXML
    private Button refreshActionTarget, printActionTarget;

    @Inject
    private SerialService serialService;

    @Inject
    private PrintService printService;

    @MVCMember
    public void setController(@Nonnull PillPatternGenerateController controller) {
        this.controller = controller;
    }

    @MVCMember
    public void setModel(@Nonnull PillPatternGenerateModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());

        Node node = loadFromFXML();
        tab.setContent(node);
        parentView.getTabGroup().getTabs().add(tab);

        labelX.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelX.text"));
        labelY.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelY.text"));
        labelR.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelR.text"));
        labelH.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelH.text"));
        labelD1.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelD1.text"));
        labelD2.setText(getApplication().getMessageSource().getMessage("PillPatternGenerator.action.LabelD2.text"));

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
        inputHActionTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isNoneBlank(newValue)) {
                model.setH(Double.parseDouble(newValue));
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
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.setFill(colorPicker.getValue());
//        gc.fillOval(model.getX(), model.getY(), model.getR(), model.getR());

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
