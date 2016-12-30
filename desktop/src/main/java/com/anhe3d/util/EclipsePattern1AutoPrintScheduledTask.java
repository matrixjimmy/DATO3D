package com.anhe3d.util;

import com.anhe3d.CommProtocol;
import com.anhe3d.EclipsePattern1GenerateModel;
import com.anhe3d.PrintService;
import com.anhe3d.SerialService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.TimerTask;

/**
 * Created by jimmy on 2016/12/19.
 */
@Slf4j
public class EclipsePattern1AutoPrintScheduledTask extends TimerTask {

    private long counter;
    private final long counterLimit;
    private final long initialESteps;
    private final SerialService serialService;
    private final PrintService printService;

    private EclipsePattern1GenerateModel model;
    private Canvas canvas;

    @Inject
    public EclipsePattern1AutoPrintScheduledTask(long counterLimit, long initialESteps, SerialService serialService, PrintService printService, EclipsePattern1GenerateModel model) {
        this.counterLimit = counterLimit;
        this.initialESteps = initialESteps;
        this.serialService = serialService;
        this.printService = printService;
        this.model = model;
        canvas = new Canvas(1000, 2000);
        canvas.setCache(true);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(model.getColor());
        gc.fillOval(260 + model.getX() - 0.5 * model.getR(), 580 + model.getY() - 0.5 * model.getR(), model.getR(), model.getR());
    }

    @Override
    public void run() {
        if (counterLimit == counter++) {
            this.cancel();
        } else {
            log.info("Task " + counter + " is Executing...");
        }
//        if (serialService.isPortOpened()) {
//            while (serialService.getPrinterBusyFlag() && serialService.isPortOpened()) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        if (serialService.isPortOpened()) {
            if (counter <= model.getD1()) { // 單純鋪粉
                BigDecimal ePrevThickness = new BigDecimal((counter - 1) * 5 + initialESteps).multiply(new BigDecimal("7.5")).divide(new BigDecimal("100"));
                BigDecimal ePrevStep = MachineStepCalculator.calculateE(ePrevThickness);
                BigDecimal eThisThickness = new BigDecimal(counter * 5 + initialESteps).multiply(new BigDecimal("7.5")).divide(new BigDecimal("100"));
                BigDecimal eThisStep = MachineStepCalculator.calculateE(eThisThickness);
                log.debug("E-PrevStep: " + ePrevStep.longValue());
                log.debug("E-ThisStep: " + eThisStep.longValue());
                log.info("E-Step: " + eThisStep.subtract(ePrevStep).abs().longValue());
                serialService.write(CommProtocol.E_GO_UP_STEP.code() + eThisStep.subtract(ePrevStep).abs().longValue());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BigDecimal zPrevThickness = new BigDecimal((counter - 1) * 5).divide(new BigDecimal("100"));
                BigDecimal zPrevStep = MachineStepCalculator.calculateZ(zPrevThickness);
                BigDecimal zThisThickness = new BigDecimal(counter * 5).divide(new BigDecimal("100"));
                BigDecimal zThisStep = MachineStepCalculator.calculateZ(zThisThickness);
                log.debug("Z-PrevStep: " + zPrevStep.longValue());
                log.debug("Z-ThisStep: " + zThisStep.longValue());
                log.info("Z-Step: " + zThisStep.subtract(zPrevStep).abs().longValue());
                serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + zThisStep.subtract(zPrevStep).abs().longValue());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                serialService.write(CommProtocol.POWDER_START);
            } else if (counter <= model.getD1() + model.getD2()) { // 鋪粉加列印
                BigDecimal ePrevThickness = new BigDecimal((counter - 1) * 5 + initialESteps).multiply(new BigDecimal("7.5")).divide(new BigDecimal("100"));
                BigDecimal ePrevStep = MachineStepCalculator.calculateE(ePrevThickness);
                BigDecimal eThisThickness = new BigDecimal(counter * 5 + initialESteps).multiply(new BigDecimal("7.5")).divide(new BigDecimal("100"));
                BigDecimal eThisStep = MachineStepCalculator.calculateE(eThisThickness);
                log.debug("E-PrevStep: " + ePrevStep.longValue());
                log.debug("E-ThisStep: " + eThisStep.longValue());
                log.info("E-Step: " + eThisStep.subtract(ePrevStep).abs().longValue());
                serialService.write(CommProtocol.E_GO_UP_STEP.code() + eThisStep.subtract(ePrevStep).abs().longValue());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BigDecimal zPrevThickness = new BigDecimal((counter - 1) * 5).divide(new BigDecimal("100"));
                BigDecimal zPrevStep = MachineStepCalculator.calculateZ(zPrevThickness);
                BigDecimal zThisThickness = new BigDecimal(counter * 5).divide(new BigDecimal("100"));
                BigDecimal zThisStep = MachineStepCalculator.calculateZ(zThisThickness);
                log.debug("Z-PrevStep: " + zPrevStep.longValue());
                log.debug("Z-ThisStep: " + zThisStep.longValue());
                log.info("Z-Step: " + zThisStep.subtract(zPrevStep).abs().longValue());
                serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + zThisStep.subtract(zPrevStep).abs().longValue());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                serialService.write(CommProtocol.POWDER_START);
                printService.print(canvas);
            }
        } else {
            this.cancel();
        }
    }
}
