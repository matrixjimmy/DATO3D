package com.anhe3d.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.griffon.runtime.core.AbstractObservable;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Created by jimmy on 2016/11/23.
 */
@NoArgsConstructor
public class ProjectDocument extends AbstractObservable {
    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private boolean dirty;
    @Getter
    private File file;

    public ProjectDocument(@Nonnull File file) {
        this.title = file.getName();
        this.file = file;
    }

    public void setTitle(String title) {
        firePropertyChange("title", this.title, this.title = title);
    }

    public void setContent(String content) {
        firePropertyChange("content", this.content, this.content = content);
    }

    public void setDirty(boolean dirty) {
        firePropertyChange("dirty", this.dirty, this.dirty = dirty);
    }

    public void setFile(File file) {
        firePropertyChange("file", this.file, this.file = file);
    }

    public void copyTo(ProjectDocument projectDocument) {
        projectDocument.title = this.title;
        projectDocument.content = this.content;
        projectDocument.dirty = this.dirty;
        projectDocument.file = this.file;
    }
}
