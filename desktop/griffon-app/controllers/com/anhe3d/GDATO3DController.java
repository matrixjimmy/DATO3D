package com.anhe3d;

import com.anhe3d.domain.ProjectDocument;
import com.anhe3d.util.TestAutoPrintScheduledTask;
import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import griffon.util.CollectionUtils;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jssc.SerialPortException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.io.File;
import java.util.Optional;
import java.util.Timer;

import static com.anhe3d.ApplicationModule.*;

@ArtifactProviderFor(GriffonController.class)
@Slf4j
public class GDATO3DController extends AbstractGriffonController {

    @MVCMember
    @Nonnull
    private GDATO3DModel model;
    @MVCMember
    @Nonnull
    private GDATO3DView view;

    @Inject
    private SerialService serialService;
    @Inject
    private PrintService printService;

    private Timer pollingStatusTimer;
    private TestAutoPrintScheduledTask task;
    private boolean isScheduled;

    @PostConstruct
    public void postConstruct() {
        pollingStatusTimer = new Timer();
//        task = new TestAutoPrintScheduledTask(serialService, printerName);
        isScheduled = false;
    }

    @Threading(Threading.Policy.SKIP)
    public void fileOpen() {
        File file = view.selectFile();
        if (file != null) {
            String mvcIdentifier = file.getName() + "-" + System.currentTimeMillis();
            ProjectDocument projectDocument = new ProjectDocument(file);
            createMVC("ProjectEditor", mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("projectDocument", projectDocument)
                            .e("tabName", file.getName())
            );
        }
    }

    public void fileClose() {
        Optional<Tab> openedTab = view.getTabGroup().getTabs().stream().filter(Tab::isSelected).findFirst();
        openedTab.ifPresent(tab -> {
            if (StringUtils.equals(PILL_PATTERN_GROUP_TABNAME, tab.getText())) {
                PillPatternGenerateController controller = resolvedPillPatternGenerateController();
                controller.destroyMVCGroup(controller.getMvcGroup().getMvcId());
            }
            if (StringUtils.equals(ECLIPSE_PATTERN_1_GROUP_TABNAME, tab.getText())) {
                EclipsePattern1GenerateController controller = resolvedEclipsePattern1GenerateController();
                controller.destroyMVCGroup(controller.getMvcGroup().getMvcId());
            }
            if (StringUtils.equals(ECLIPSE_PATTERN_2_GROUP_TABNAME, tab.getText())) {
                EclipsePattern2GenerateController controller = resolvedEclipsePattern2GenerateController();
                controller.destroyMVCGroup(controller.getMvcGroup().getMvcId());
            }
            if (StringUtils.equals(TUBE_PATTERN_GROUP_TABNAME, tab.getText())) {
                TubePatternGenerateController controller = resolvedTubePatternGenerateController();
                controller.destroyMVCGroup(controller.getMvcGroup().getMvcId());
            }
        });
    }

    public void fileSave() {
        ProjectEditorController controller = resolvedProjectEditorController();
        if (controller != null) {
            controller.saveFile();
        }
    }

    public void fileExit() {
        getApplication().shutdown();
    }

    public void serialConnect() {
        if (!serialService.isPortOpened()) {
            String mvcIdentifier = "MachineInterface-" + System.currentTimeMillis();
            String selectedSerialName = view.getSerialName().getValue();
            runInsideUIAsync(() -> view.getSerialConnStatus().setText("Connecting..."));
            try {
                serialService.initSerialPort(selectedSerialName);
                runInsideUIAsync(() -> view.getSerialConnStatus().setText("Connected"));
            } catch (SerialPortException e) {
                log.error("Serial Connection Failed...", e);
                runInsideUIAsync(() -> view.getSerialConnStatus().setText("Connect Failed"));
            }
            if (serialService.isPortOpened()) {
                createMVC("MachineInterface", mvcIdentifier,
                        CollectionUtils.<String, Object>map()
                                .e("tabName", "Control Interface")
                );
                view.afterSerialConnected();
            }
        }
    }

    @PreDestroy
    public void serialDisconnect() throws SerialPortException {
        if (serialService.isPortOpened()) {
            serialService.closePort();
            runInsideUIAsync(() -> view.getSerialConnStatus().setText("Disconnected"));
            MachineInterfaceController controller = resolvedMachineInterfaceController();
            if (controller != null) {
                controller.onClosePort();
            }
            if (!serialService.isPortOpened()) {
                view.afterSerialDisconnected();
            }
        }
    }

    public void serialDisplayMsg() {
        if (serialService.isPortOpened()) {
            runInsideUIAsync(() -> {
                view.getSerialMsg().setText("");
                view.getSerialMsg().setText(serialService.read());
            });
        } else {
            runInsideUIAsync(() -> view.getSerialMsg().setText("No Connection..."));
        }
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void serialCheckBusy() {
        //serialService.bindExecutingEventListener();
        if (serialService.getPrinterBusyFlag()) {
            System.out.println("Serial Busy ...");
        } else {
            System.out.println("Serial not Busy");
        }
        //serialService.bindDefaultEventListener();
    }

//    public void resetAll() {
//        serialService.write(CommProtocol.RESET_33FJ);
//    }

    private int index = 0;

    public void autoPrintSinglePage() {

        if (view.getAutoPrintPagesActionTarget().isSelected()) { // auto print pages setting
//            serialService.write(CommProtocol.E_GO_TO_BOTTOM);   // 50s
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            serialService.write(CommProtocol.Z_GO_TO_TOP);      // 5m30s

//            try {
//                Thread.sleep(335000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (index++ <= 1) {
                task = new TestAutoPrintScheduledTask(serialService, printService, 100, index);
                pollingStatusTimer.schedule(task, 0, 60 * 1000);
            }
        } else { // print single page setting
            /* 這部分的範圍已經落在接近槽體極限的部分 */
            Rectangle rectangle = new Rectangle();

            rectangle.setX(130);
            rectangle.setY(330);
            rectangle.setWidth(500);
            rectangle.setHeight(500);
            rectangle.setArcWidth(20);
            rectangle.setArcHeight(20);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(new Color(Math.random(), Math.random(), Math.random(), 1));
            rectangle.setStrokeWidth((int) (Math.random() * 10));
            Node node = rectangle;

            PrinterJob job = PrinterJob.createPrinterJob(view.getPrinterName().getValue());
            PageLayout pageLayout = view.getPrinterName().getValue().createPageLayout(Paper.A3,
                    PageOrientation.PORTRAIT,
                    Printer.MarginType.HARDWARE_MINIMUM);
            job.getJobSettings().setPageLayout(pageLayout);
            if (job != null) {
                boolean success = job.printPage(node);
                if (success) {
                    job.endJob();
                }
            }

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

    public void autoPrintPages() throws InterruptedException {
        if (view.getAutoPrintPagesActionTarget().isSelected()) {
            pollingStatusTimer = new Timer();
        } else {
            pollingStatusTimer.cancel();
            pollingStatusTimer.purge();
        }

    }

    public void generatePillView() {
        if (resolvedPillPatternGenerateController() == null) {
            String mvcIdentifier = PILL_PATTERN_GROUP_NAME + "-" + System.currentTimeMillis();
            createMVC(PILL_PATTERN_GROUP_NAME, mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("tabName", PILL_PATTERN_GROUP_TABNAME)
            );
        }
    }

    public void generateEclipsePattern1View() {
        if (resolvedEclipsePattern1GenerateController() == null) {
            String mvcIdentifier = ECLIPSE_PATTERN_1_GROUP_NAME + "-" + System.currentTimeMillis();
            createMVC(ECLIPSE_PATTERN_1_GROUP_NAME, mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("tabName", ECLIPSE_PATTERN_1_GROUP_TABNAME)
            );
        }
    }

    public void generateEclipsePattern2View() {
        if (resolvedEclipsePattern2GenerateController() == null) {
            String mvcIdentifier = ECLIPSE_PATTERN_2_GROUP_NAME + "-" + System.currentTimeMillis();
            createMVC(ECLIPSE_PATTERN_2_GROUP_NAME, mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("tabName", ECLIPSE_PATTERN_2_GROUP_TABNAME)
            );
        }
    }

    public void generateTubePatternView() {
        if (resolvedTubePatternGenerateController() == null) {
            String mvcIdentifier = TUBE_PATTERN_GROUP_NAME + "-" + System.currentTimeMillis();
            createMVC(TUBE_PATTERN_GROUP_NAME, mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("tabName", TUBE_PATTERN_GROUP_TABNAME)
            );
        }
    }

    public void testCanvas() {
        if (resolvedPillPatternGenerateController() == null) {
            String mvcIdentifier = "pill-pattern-generate-" + System.currentTimeMillis();
            createMVC("pill-pattern-generate", mvcIdentifier,
                    CollectionUtils.<String, Object>map()
                            .e("tabName", "Generate Pill")
            );
        }
    }

    @Nullable
    private ProjectEditorController resolvedProjectEditorController() {
        if (StringUtils.isNotBlank(model.getMvcIdentifier())) {
            return getApplication().getMvcGroupManager()
                    .findController(model.getMvcIdentifier(), ProjectEditorController.class);
        }
        return null;
    }

    @Nullable
    private MachineInterfaceController resolvedMachineInterfaceController() {
        Optional<String> groupName =
                getMvcGroup()
                        .getChildrenGroups()
                        .keySet().stream()
                        .filter(name -> StringUtils.startsWith(name, "MachineInterface"))
                        .findFirst();
        return groupName.map(s -> getApplication().getMvcGroupManager()
                .findController(s, MachineInterfaceController.class))
                .orElse(null);
    }

    @Nullable
    private PillPatternGenerateController resolvedPillPatternGenerateController() {
        Optional<String> groupName =
                getMvcGroup()
                        .getChildrenGroups()
                        .keySet().stream()
                        .filter(name -> StringUtils.startsWith(name, PILL_PATTERN_GROUP_NAME))
                        .findFirst();
        return groupName.map(s -> getApplication().getMvcGroupManager()
                .findController(s, PillPatternGenerateController.class))
                .orElse(null);
    }

    @Nullable
    private EclipsePattern1GenerateController resolvedEclipsePattern1GenerateController() {
        Optional<String> groupName =
                getMvcGroup()
                        .getChildrenGroups()
                        .keySet().stream()
                        .filter(name -> StringUtils.startsWith(name, ECLIPSE_PATTERN_1_GROUP_NAME))
                        .findFirst();
        return groupName.map(s -> getApplication().getMvcGroupManager()
                .findController(s, EclipsePattern1GenerateController.class))
                .orElse(null);
    }

    @Nullable
    private EclipsePattern2GenerateController resolvedEclipsePattern2GenerateController() {
        Optional<String> groupName =
                getMvcGroup()
                        .getChildrenGroups()
                        .keySet().stream()
                        .filter(name -> StringUtils.startsWith(name, ECLIPSE_PATTERN_2_GROUP_NAME))
                        .findFirst();
        return groupName.map(s -> getApplication().getMvcGroupManager()
                .findController(s, EclipsePattern2GenerateController.class))
                .orElse(null);
    }

    @Nullable
    private TubePatternGenerateController resolvedTubePatternGenerateController() {
        Optional<String> groupName =
                getMvcGroup()
                        .getChildrenGroups()
                        .keySet().stream()
                        .filter(name -> StringUtils.startsWith(name, TUBE_PATTERN_GROUP_NAME))
                        .findFirst();
        return groupName.map(s -> getApplication().getMvcGroupManager()
                .findController(s, TubePatternGenerateController.class))
                .orElse(null);
    }
}