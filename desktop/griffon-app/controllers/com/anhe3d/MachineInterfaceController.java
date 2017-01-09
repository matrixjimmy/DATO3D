package com.anhe3d;

import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.scene.control.TextInputDialog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@ArtifactProviderFor(GriffonController.class)
@Slf4j
public class MachineInterfaceController extends AbstractGriffonController {

    @MVCMember
    @Nonnull
    private MachineInterfaceModel model;

    @MVCMember
    @Nonnull
    private MachineInterfaceView view;

    @Inject
    private SerialService serialService;

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {

    }

    public void onClosePort() {
        serialService.closePort();
        destroyMVCGroup(getMvcGroup().getMvcId());
    }

    public void pPowerButton() {
        log.info("Press Printer Power Button");
        serialService.write(CommProtocol.PRESS_POWER);
    }

    public void pInkButton() {
        log.info("Press Printer Ink Button");
        serialService.write(CommProtocol.PRESS_INK);
    }

    public void pPaperButton() {
        log.info("Press Printer Paper Button");
        serialService.write(CommProtocol.PRESS_PAPER);
    }

    public void pDeleteButton() {
        log.info("Press Printer Delete Button");
        serialService.write(CommProtocol.PRESS_DELETE);
    }

    public void pStartPrint() {
        log.info("Start Print");
        serialService.write(CommProtocol.START_PRINT);
    }

    public void pStopPrint() {
        log.info("Stop Print");
        serialService.write(CommProtocol.STOP_PRINT);
    }

    public void pStartPowder() {
        log.info("Start Powder");
        serialService.write(CommProtocol.POWDER_START);
    }

    public void eUpMostButton() {
        log.info("E go to top");
        serialService.write(CommProtocol.E_GO_TO_TOP);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void eUpStepButton() {
        runInsideUISync(() -> {
            TextInputDialog dialog = new TextInputDialog("100");
            dialog.setTitle("Extrusion Tank Go Up Step");
            dialog.setHeaderText("Extrusion Tank");
            dialog.setContentText("Please enter how much step:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> serialService.write(CommProtocol.E_GO_UP_STEP.code() + value));
        });
    }

    public void eDownStepButton() {
        runInsideUISync(() -> {
            TextInputDialog dialog = new TextInputDialog("100");
            dialog.setTitle("Extrusion Tank Go Down Step");
            dialog.setHeaderText("Extrusion Tank");
            dialog.setContentText("Please enter how much step:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> serialService.write(CommProtocol.E_GO_DOWN_STEP.code() + value));
        });
    }

    public void eDownMostButton() {
        log.info("E go to bottom");
        serialService.write(CommProtocol.E_GO_TO_BOTTOM);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        model.extrudeCurrentPositionProperty().setValue("0");
    }

    public void eFlatButton() {
        log.info("E go to top flat");
        serialService.write(CommProtocol.E_GO_FLAT);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void zUpMostButton() {
        log.info("Z go to Top");
        serialService.write(CommProtocol.Z_GO_TO_TOP);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        model.constructionCurrentPositionProperty().setValue("0");
    }

    public void zUpStepButton() {
        runInsideUISync(() -> {
            TextInputDialog dialog = new TextInputDialog("100");
            dialog.setTitle("Construction Tank Go Up Step");
            dialog.setHeaderText("Construction Tank");
            dialog.setContentText("Please enter how much step:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> serialService.write(CommProtocol.Z_GO_UP_STEP.code() + value));
        });
    }

    public void zDownStepButton() {
        runInsideUISync(() -> {
            TextInputDialog dialog = new TextInputDialog("100");
            dialog.setTitle("Construction Tank Go Down Step");
            dialog.setHeaderText("Construction Tank");
            dialog.setContentText("Please enter how much step:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + value));
        });
    }

    public void zDownMostButton() {
        log.info("Z go to bottom");
        serialService.write(CommProtocol.Z_GO_TO_BOTTOM);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void zFlatButton() {
        log.info("Z go to top flat");
        serialService.write(CommProtocol.Z_GO_FLAT);
        while (serialService.getPlatformBusyFlag()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void mGoToStartButton() {
        serialService.write(CommProtocol.GO_TO_START);
    }

    public void mGoToEndButton() {
        serialService.write(CommProtocol.GO_TO_END);
    }

    public void mServoUpButton() {
        serialService.write(CommProtocol.SERVO_UP);
    }

    public void mServoDownButton() {
        serialService.write(CommProtocol.SERVO_DOWN);
    }

    public void mServoStopButton() {
        serialService.write(CommProtocol.SERVO_STOP);
    }

    public void mRollerForwardButton() {
        serialService.write(CommProtocol.ROLLER_FORWARD);
    }

    public void mRollerBackwardButton() {
        serialService.write(CommProtocol.ROLLER_BACKWARD);
    }

    public void mRollerStopButton() {
        serialService.write(CommProtocol.ROLLER_STOP);
    }

    public void mRollerSendSettingButton() {
        int speed = 100;
        if (StringUtils.isNotBlank(view.getRCurrentSpeed().getText()) &&
                StringUtils.isNumeric(view.getRCurrentSpeed().getText())) {
            speed = Integer.parseInt(view.getRCurrentSpeed().getText());
        }
        serialService.write(CommProtocol.SET_ROLLER_SPEED + " " + String.valueOf(speed));
    }
}
