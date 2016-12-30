package com.anhe3d;

import griffon.core.artifact.GriffonView;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

@ArtifactProviderFor(GriffonView.class)
public class TubePatternGenerateView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    private GDATO3DView parentView;

    @MVCMember
    @Nonnull
    private TubePatternGenerateController controller;

    @MVCMember
    @Nonnull
    private TubePatternGenerateModel model;

    @MVCMember
    @Nonnull
    private String tabName;

    private Tab tab;

    private DirectoryChooser directoryChooser;

    private FileChooser fileChooser;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    @Getter
    private ImageView imageView;

    @MVCMember
    public void setController(@Nonnull TubePatternGenerateController controller) {
        this.controller = controller;
    }

    @MVCMember
    public void setModel(@Nonnull TubePatternGenerateModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());

        Node node = loadFromFXML();
        tab.setContent(node);
        tab.setClosable(true);

        parentView.getTabGroup().getTabs().add(tab);

        colorPicker.setOnAction(e -> {
            Color fxColor = colorPicker.getValue();

            float red = (float) fxColor.getRed();
            float green = (float) fxColor.getGreen();
            float blue = (float) fxColor.getBlue();
            float opacity = (float) fxColor.getOpacity();
            java.awt.Color awtColor = new java.awt.Color(red, green, blue, opacity);
            model.setColor(awtColor);
        });

        directoryChooser = new DirectoryChooser();
        fileChooser = new FileChooser();

        connectActions(node, controller);
    }

    @Override
    public void mvcGroupDestroy() {
        runInsideUISync(() -> parentView.getTabGroup().getTabs().remove(tab));
    }

    @Nullable
    File selectFileFolderPath() {
        Window window = (Window) getApplication().getWindowManager().getStartingWindow();
        return directoryChooser.showDialog(window);
    }

    @Nullable
    File selectReadFile() {
        Window window = (Window) getApplication().getWindowManager().getStartingWindow();
        return fileChooser.showOpenDialog(window);
    }

    void setImageView(Image image) {
        this.imageView.setImage(image);
    }
}
