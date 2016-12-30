package com.anhe3d;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

@ArtifactProviderFor(GriffonModel.class)
public class EclipsePattern1GenerateModel extends AbstractGriffonModel {

    @Getter
    private Double x, y, r, initialE, d1, d2;
    @Getter
    private Color color = Color.WHITE;

    public void setX(Double x) {
        firePropertyChange("x", this.x, this.x = x);
    }

    public void setY(Double y) {
        firePropertyChange("y", this.y, this.y = y);
    }

    public void setR(Double r) {
        firePropertyChange("r", this.r, this.r = r);
    }

    public void setInitialE(Double initialE) {
        firePropertyChange("initialE", this.initialE, this.initialE = initialE);
    }

    public void setD1(Double d1) {
        firePropertyChange("d1", this.d1, this.d1 = d1);
    }

    public void setD2(Double d2) {
        firePropertyChange("d2", this.d2, this.d2 = d2);
    }

    public void setColor(Color color) {
        firePropertyChange("color", this.color, this.color = color);
    }
}