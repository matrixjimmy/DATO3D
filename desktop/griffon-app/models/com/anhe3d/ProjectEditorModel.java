package com.anhe3d;

import com.anhe3d.domain.ProjectDocument;
import griffon.core.artifact.GriffonModel;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

import javax.annotation.Nonnull;

@ArtifactProviderFor(GriffonModel.class)
@Slf4j
public class ProjectEditorModel extends AbstractGriffonModel {
    @MVCMember @Nonnull
    private ProjectDocument projectDocument;

    public ProjectDocument getProjectDocument() {
        return projectDocument;
    }

    public void setProjectDocument(ProjectDocument projectDocument) {
        if (projectDocument.getFile() != null) {
            log.info(projectDocument.getFile().getName());
        } else {
            log.warn("file is null");
        }
        firePropertyChange("projectDocument", this.projectDocument, this.projectDocument = projectDocument);
    }
}