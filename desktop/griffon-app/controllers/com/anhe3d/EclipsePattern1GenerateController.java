package com.anhe3d;

import com.anhe3d.util.EclipsePattern1AutoPrintScheduledTask;
import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;
import java.util.Timer;

@ArtifactProviderFor(GriffonController.class)
public class EclipsePattern1GenerateController extends AbstractGriffonController {
    @MVCMember
    @Nonnull
    private EclipsePattern1GenerateModel model;

    @MVCMember
    @Nonnull
    private EclipsePattern1GenerateView view;

    @Inject
    private SerialService serialService;
    @Inject
    private PrintService printService;

    private Timer timer;
    private EclipsePattern1AutoPrintScheduledTask task;

    @PostConstruct
    public void postConstruct() {
        timer = new Timer();
    }

    @MVCMember
    public void setModel(@Nonnull EclipsePattern1GenerateModel model) {
        this.model = model;
    }

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        model.setX(200.0);
        model.setY(100.0);
        model.setR(90.0);

        model.setInitialE(0.0);
        model.setD1(0.0);

        model.setD2(40.0);
    }

    @Override
    public void mvcGroupDestroy() {
        timer.purge();
        timer.cancel();
        super.mvcGroupDestroy();
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void print() {
        task = new EclipsePattern1AutoPrintScheduledTask((long) (model.getD1() + model.getD2()), model.getInitialE().longValue(), serialService, printService, model);
        timer.scheduleAtFixedRate(task, 0, 60000);
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