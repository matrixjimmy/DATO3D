package com.anhe3d;

import com.anhe3d.domain.ProjectDocument;
import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
@Slf4j
public class ProjectEditorController extends AbstractGriffonController {
    @MVCMember
    @Nonnull
    private ProjectEditorModel model;
    @MVCMember
    @Nonnull
    private ProjectEditorView view;

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        model.setProjectDocument((ProjectDocument) args.get("projectDocument"));
        runOutsideUI(() -> {
            try {
                final String content = FileUtils.readFileToString(model.getProjectDocument().getFile(), Charset.defaultCharset());
                runInsideUIAsync(() -> model.getProjectDocument().setContent(content));
            } catch (Exception e) {
                log.warn("cannot open file", e);
            }
        });
    }

    public void saveFile() {
        try {
            FileUtils.writeStringToFile(model.getProjectDocument().getFile(), view.getEditor().getText(), Charset.defaultCharset());
            runInsideUIAsync(()->model.getProjectDocument().setContent(view.getEditor().getText()));
        } catch (IOException e) {
            log.warn("cannot save file", e);
        }
    }

    public void closeFile() {
        destroyMVCGroup(getMvcGroup().getMvcId());
    }
}