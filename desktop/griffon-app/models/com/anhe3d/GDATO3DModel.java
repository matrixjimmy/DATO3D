package com.anhe3d;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

@ArtifactProviderFor(GriffonModel.class)
@Slf4j
public class GDATO3DModel extends AbstractGriffonModel {

    private static final String MVC_IDENTIFIER = "GDATO3DContainer";

//    private final ProjectDocumentModel projectDocumentModel = new ProjectDocumentModel();

    private String mvcIdentifier;

    public GDATO3DModel() {
//        addPropertyChangeListener(MVC_IDENTIFIER, (e) -> {
//
//            ProjectDocument projectDocument;
//            if (e.getNewValue() != null) {
//                ProjectEditorModel model = getApplication().getMvcGroupManager().getModel(mvcIdentifier, ProjectEditorModel.class);
//                projectDocument = model.getProjectDocument();
//            } else {
//                projectDocument = new ProjectDocument();
//            }
//            projectDocumentModel.setProjectDocument(projectDocument);
//        });
    }

//    public ProjectDocumentModel getProjectDocumentModel() {
//        return projectDocumentModel;
//    }

    public String getMvcIdentifier() {
        return mvcIdentifier;
    }

    public void setMvcIdentifier(String mvcIdentifier) {
        firePropertyChange(MVC_IDENTIFIER, this.mvcIdentifier, this.mvcIdentifier = mvcIdentifier);
    }
}