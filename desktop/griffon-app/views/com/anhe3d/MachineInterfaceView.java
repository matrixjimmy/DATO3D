package com.anhe3d;

import griffon.core.artifact.GriffonView;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;

@ArtifactProviderFor(GriffonView.class)
public class MachineInterfaceView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    private GDATO3DView parentView;

    @MVCMember
    @Nonnull
    private MachineInterfaceController controller;

    @MVCMember
    @Nonnull
    private MachineInterfaceModel model;

    @MVCMember
    @Nonnull
    private String tabName;

    private Tab tab;

    @FXML
    private Button eUpMostButtonActionTarget, eUpStepButtonActionTarget, eDownStepButtonActionTarget, eDownMostButtonActionTarget, eFlatButtonActionTarget;

    @FXML
    private Button zUpMostButtonActionTarget, zUpStepButtonActionTarget, zDownStepButtonActionTarget, zDownMostButtonActionTarget, zFlatButtonActionTarget;

    @FXML
    private Button pPowerButtonActionTarget, pInkButtonActionTarget, pPaperButtonActionTarget, pDeleteButtonActionTarget, pStartPrintActionTarget, pStopPrintActionTarget, pStartPowderActionTarget;

    @FXML
    private Button mGoToStartButtonActionTarget, mGoToEndButtonActionTarget, mServoUpButtonActionTarget, mServoDownButtonActionTarget, mServoStopButtonActionTarget, mRollerForwardButtonActionTarget, mRollerBackwardButtonActionTarget, mRollerStopButtonActionTarget, mRollerSendSettingButtonActionTarget;

    @FXML
    private TextField eCurrentPos, zCurrentPos;

    @FXML
    @Getter
    private TextField rCurrentSpeed;

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());

        Node node = loadFromFXML();
        tab.setContent(node);
        parentView.getTabGroup().getTabs().add(tab);

        connectActions(node, controller);
        // Custom injecting the button text
        eUpMostButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.UpMost.text"));
        eUpStepButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.UpStep.text"));
        eDownStepButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.DownStep.text"));
        eDownMostButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.DownMost.text"));
        eFlatButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.Flat.text"));

        zUpMostButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.UpMost.text"));
        zUpStepButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.UpStep.text"));
        zDownStepButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.DownStep.text"));
        zDownMostButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.DownMost.text"));
        zFlatButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.Flat.text"));

        pPowerButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.PowerButton.text"));
        pInkButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.InkButton.text"));
        pPaperButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.PaperButton.text"));
        pDeleteButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.DeleteButton.text"));
        pStartPrintActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.StartPrint.text"));
        pStopPrintActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.StopPrint.text"));
        pStartPowderActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.StartPowder.text"));

        mGoToStartButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.GoToStartButton.text"));
        mGoToEndButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.GoToEndButton.text"));
        mServoUpButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.ServoUpButton.text"));
        mServoDownButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.ServoDownButton.text"));
        mServoStopButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.ServoStopButton.text"));
        mRollerForwardButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.RollerForwardButton.text"));
        mRollerBackwardButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.RollerBackwardButton.text"));
        mRollerStopButtonActionTarget.setText(getApplication().getMessageSource().getMessage("MachineInterface.action.RollerStopButton.text"));
        mRollerSendSettingButtonActionTarget.setText("設定滾粉速度");

        // Bind view attribute with model attribute
        model.extrudeCurrentPositionProperty().bindBidirectional(eCurrentPos.textProperty());
        model.constructionCurrentPositionProperty().bindBidirectional(zCurrentPos.textProperty());
    }

    @Override
    public void mvcGroupDestroy() {
        runInsideUISync(() -> parentView.getTabGroup().getTabs().remove(tab));
    }
}
