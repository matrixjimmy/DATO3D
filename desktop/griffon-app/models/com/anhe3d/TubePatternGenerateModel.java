package com.anhe3d;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import lombok.Setter;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

import java.awt.*;

@ArtifactProviderFor(GriffonModel.class)
public class TubePatternGenerateModel extends AbstractGriffonModel {

    @Setter
    private Color color;

    public Color getColor() {
        return color;
    }
}