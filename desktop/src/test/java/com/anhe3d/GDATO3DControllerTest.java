package com.anhe3d;

import griffon.core.artifact.ArtifactManager;
import griffon.core.test.GriffonUnitRule;
import griffon.core.test.TestFor;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

@TestFor(GDATO3DController.class)
public class GDATO3DControllerTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel();
    }

    @Inject
    private ArtifactManager artifactManager;

    private GDATO3DController controller;

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule();

    @Test
    public void executeClickAction() {
        // given:
        // GDATO3DModel model = artifactManager.newInstance(GDATO3DModel.class);
        // controller.setModel(model);

        // when:
        // controller.invokeAction("click");
        // await().atMost(2, SECONDS);

        // then:
        // assertEquals("1", model.getClickCount());
    }
}
