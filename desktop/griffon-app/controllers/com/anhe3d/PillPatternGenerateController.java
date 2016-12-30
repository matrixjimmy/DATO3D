package com.anhe3d;

import com.anhe3d.util.PillPatternAutoPrintScheduledTask;
import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;
import java.util.Timer;

@ArtifactProviderFor(GriffonController.class)
@Slf4j
public class PillPatternGenerateController extends AbstractGriffonController {
    @MVCMember
    @Nonnull
    private PillPatternGenerateModel model;

    @MVCMember
    @Nonnull
    private PillPatternGenerateView view;

    @Inject
    private SerialService serialService;
    @Inject
    private PrintService printService;

    private Timer timer;
    private PillPatternAutoPrintScheduledTask task;

    @PostConstruct
    public void postConstruct() {
        timer = new Timer();
    }

    @MVCMember
    public void setModel(@Nonnull PillPatternGenerateModel model) {
        this.model = model;
    }

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {

    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void print() {
        task = new PillPatternAutoPrintScheduledTask((int) (model.getD1() + model.getD2()), serialService, printService, model);
        timer.scheduleAtFixedRate(task, 0, 90000);
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void refresh() {
        final Canvas canvas = new Canvas(400, 400);
        canvas.setCache(true);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(model.getColor());
        gc.fillOval(model.getX() - 0.5 * model.getR(), model.getY() - 0.5 * model.getR(), model.getR(), model.getR());
        view.getCanvasGroup().getChildren().clear();
        view.getCanvasGroup().getChildren().add(canvas);
    }
}