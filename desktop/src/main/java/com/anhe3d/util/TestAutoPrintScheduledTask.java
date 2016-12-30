package com.anhe3d.util;

import com.anhe3d.CommProtocol;
import com.anhe3d.PrintService;
import com.anhe3d.SerialService;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.TimerTask;

/**
 * Created by jimmy on 2016/12/14.
 */
@Slf4j
public class TestAutoPrintScheduledTask extends TimerTask {

    private long counter;
    private final long counterLimit;
    private final int index;
    private final SerialService serialService;
    private final PrintService printService;

    private PrinterJob currentJob;

    @Inject
    public TestAutoPrintScheduledTask(SerialService serialService, PrintService printService, long counterLimit, int index) {
        counter = 0;
        this.counterLimit = counterLimit;
        this.serialService = serialService;
        this.printService = printService;
        this.index = index;
    }

    @Override
    public void run() {
        if (counterLimit == counter++) {
            this.cancel();
        } else {
            log.info("Task " + counter + " is Executing...");
        }
        if (serialService.isPortOpened()) {
            Group group = new Group();

            Circle pill = new Circle(260 + 100 + 5 * counter, 600 + 100 + 10 * index, 1, Color.LIGHTBLUE);
            pill.setStrokeWidth(0);
//            pill.setStroke(Color.CYAN);
            group.getChildren().add(pill);

            printService.print(group);
            serialService.write(CommProtocol.E_GO_UP_STEP.code() + " " + 500);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serialService.write(CommProtocol.Z_GO_DOWN_STEP.code() + " " + 300);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serialService.write(CommProtocol.POWDER_START);
        }
    }
}
