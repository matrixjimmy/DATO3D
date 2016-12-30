package com.anhe3d;

import griffon.core.artifact.GriffonView;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;
import java.util.Objects;

@ArtifactProviderFor(GriffonView.class)
public class ProjectEditorView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    private ProjectEditorModel model;

    @MVCMember
    @Nonnull
    private GDATO3DView parentView;
    @MVCMember
    @Nonnull
    private String tabName;

    @FXML
    private TextArea editor;

    private Tab tab;

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());
        tab.setContent(loadFromFXML());
        parentView.getTabGroup().getTabs().add(tab);

        model.getProjectDocument().addPropertyChangeListener("contents", (e) -> editor.setText((String) e.getNewValue()));

        editor.textProperty().addListener((observable, oldValue, newValue) ->
                model.getProjectDocument().setDirty(!Objects.equals(editor.getText(), model.getProjectDocument().getContent())));
    }

    public TextArea getEditor() {
        return editor;
    }

    @Override
    public void mvcGroupDestroy() {
        runInsideUISync(() -> parentView.getTabGroup().getTabs().remove(tab));
    }
}
