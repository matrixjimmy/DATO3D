package com.anhe3d;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

import javax.annotation.Nonnull;

@ArtifactProviderFor(GriffonModel.class)
public class MachineInterfaceModel extends AbstractGriffonModel {

    private StringProperty extrudeCurrentPosition;

    private StringProperty constructionCurrentPosition;

    public String getExtrudeCurrentPosition() {
        return extrudeCurrentPosition == null ? null : extrudeCurrentPosition.get();
    }

    @Nonnull
    public StringProperty extrudeCurrentPositionProperty() {
        if (extrudeCurrentPosition == null) {
            extrudeCurrentPosition = new SimpleStringProperty(this, "extrudeCurrentPosition", "");
        }
        return extrudeCurrentPosition;
    }

    public void setExtrudeCurrentPosition(String extrudeCurrentPosition) {
        this.extrudeCurrentPosition.set(extrudeCurrentPosition);
    }

    public String getConstructionCurrentPosition() {
        return constructionCurrentPosition == null ? null : constructionCurrentPosition.get();
    }

    @Nonnull
    public StringProperty constructionCurrentPositionProperty() {
        if (constructionCurrentPosition == null) {
            constructionCurrentPosition = new SimpleStringProperty(this, "constructionCurrentPosition", "");
        }
        return constructionCurrentPosition;
    }

    public void setConstructionCurrentPosition(String constructionCurrentPosition) {
        this.constructionCurrentPosition.set(constructionCurrentPosition);
    }
}