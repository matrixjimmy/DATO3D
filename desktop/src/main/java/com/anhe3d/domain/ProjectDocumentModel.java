package com.anhe3d.domain;

import griffon.util.GriffonClassUtils;

import java.beans.PropertyChangeListener;

/**
 * Created by jimmy on 2016/11/23.
 */
public class ProjectDocumentModel extends ProjectDocument {

    private ProjectDocument projectDocument;

    private final PropertyChangeListener proxyUpdater = (e) -> GriffonClassUtils.setPropertyValue(this, e.getPropertyName(), e.getNewValue());

    public ProjectDocumentModel() {
        addPropertyChangeListener("projectDocument", (e) -> {
            if (e.getOldValue() instanceof ProjectDocument) {
                ((ProjectDocument) e.getOldValue()).removePropertyChangeListener(proxyUpdater);
            }
            if (e.getNewValue() instanceof ProjectDocument) {
                ((ProjectDocument) e.getNewValue()).addPropertyChangeListener(proxyUpdater);
                ((ProjectDocument) e.getNewValue()).copyTo(ProjectDocumentModel.this);
            }
        });
    }

    public ProjectDocument getProjectDocument() {
        return projectDocument;
    }

    public void setProjectDocument(ProjectDocument projectDocument) {
        firePropertyChange("projectDocument", this.projectDocument, this.projectDocument = projectDocument);
    }
}
