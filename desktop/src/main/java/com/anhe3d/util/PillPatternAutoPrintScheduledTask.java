package com.anhe3d.util;

import com.anhe3d.CommProtocol;
import com.anhe3d.PillPatternGenerateModel;
import com.anhe3d.PrintService;
import com.anhe3d.SerialService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.TimerTask;

/**
 * Created by jimmy on 2016/12/19.
 */
@Slf4j
public class PillPatternAutoPrintScheduledTask extends TimerTask {

    private long counter;
    private final long counterLimit;
    private final SerialService serialService;
    private final PrintService printService;

    private PillPatternGenerateModel model;
    private Canvas canvas;

    @Inject
    public PillPatternAutoPrintScheduledTask(long counterLimit, SerialService serialService, PrintService printService, PillPatternGenerateModel model) {
        this.counterLimit = counterLimit;
        this.serialService = serialService;
        this.printService = printService;
        this.model = model;
        canvas = new Canvas(1000, 2000);
        canvas.setCache(true);
        GraphicsContext gc = canvas.getGraphicsContext2D();

//        gc.setFill(model.getColor());
        gc.setFill(Color.RED);
        gc.fillOval(260 + model.getX() - 0.5 * model.getR(), 600 + 90 + model.getY() - 0.5 * model.getH(), model.getR(), model.getH());

        gc.setFill(Color.GREEN);
        gc.fillOval(260 + model.getX() - 0.5 * model.getR(), 600 + model.getY() - 0.5 * model.getR(), model.getR(), model.getR());

//        gc.setFill(Color.BLUE);
//        gc.fillOval(260 + model.getX() - 0.5 * model.getR(), 600 - 90 + model.getY() - 0.5 * model.getR() + 10, model.getR() - 20, model.getR() - 20);
//        gc.setFill(Color.WHITE);
//        gc.fillOval(260 + model.getX() - 0.5 * model.getR(), 600 - 90 + model.getY() - 0.5 * model.getR() + 10, model.getR() - 20, model.getR() - 20);
    }

    @Override
    public void run() {
        if (counterLimit == counter++) {
            this.cancel();
            //serialService.bindDefaultEventListener();
        } else {
            log.info("Task " + counter + " is Executing...");
        }
        if (serialService.isPortOpened()) {
            //serialService.bindExecutingEventListener();
            while (serialService.getPrinterBusyFlag() && serialService.isPortOpened()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (serialService.isPortOpened()) {
            if (counter <= model.getD1()) { // 單純鋪粉
//                serialService.write(CommProtocol.E_GO_UP_STEP.code() + 600);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + 300);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                serialService.write(CommProtocol.POWDER_START);
            } else if (counter <= model.getD2()) { // 鋪粉加列印
                printService.print(canvas);
//                serialService.write(CommProtocol.E_GO_UP_STEP.code() + 800);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                BigDecimal thickness = new BigDecimal((1000 + counter * 5)).divide(new BigDecimal("100"));
                serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + MachineStepCalculator.calculateZ(thickness));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                serialService.write(CommProtocol.POWDER_START);
            }
        } else {
            this.cancel();
            //serialService.bindDefaultEventListener();
        }
    }
}
