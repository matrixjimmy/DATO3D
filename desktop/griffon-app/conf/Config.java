import com.anhe3d.ApplicationModule;
import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static griffon.util.CollectionUtils.map;
import static java.util.Arrays.asList;

public class Config extends AbstractMapResourceBundle {

    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
                .e("application", map()
                        .e("title", "DATO3D")
                        .e("startupGroups", asList("GDATO3D"))
                        .e("autoShutdown", true)
                )
                .e("mvcGroups", map()
                        .e("GDATO3D", map()
                                .e("model", "com.anhe3d.GDATO3DModel")
                                .e("view", "com.anhe3d.GDATO3DView")
                                .e("controller", "com.anhe3d.GDATO3DController")
                        )
                        .e("ProjectEditor", map()
                                .e("model", "com.anhe3d.ProjectEditorModel")
                                .e("view", "com.anhe3d.ProjectEditorView")
                                .e("controller", "com.anhe3d.ProjectEditorController")
                        )
                        .e("MachineInterface", map()
                                .e("model", "com.anhe3d.MachineInterfaceModel")
                                .e("view", "com.anhe3d.MachineInterfaceView")
                                .e("controller", "com.anhe3d.MachineInterfaceController")
                        )
                        .e(ApplicationModule.PILL_PATTERN_GROUP_NAME, map()
                                .e("model", "com.anhe3d.PillPatternGenerateModel")
                                .e("view", "com.anhe3d.PillPatternGenerateView")
                                .e("controller", "com.anhe3d.PillPatternGenerateController")
                        )
                        .e(ApplicationModule.ECLIPSE_PATTERN_1_GROUP_NAME, map()
                                .e("model", "com.anhe3d.EclipsePattern1GenerateModel")
                                .e("view", "com.anhe3d.EclipsePattern1GenerateView")
                                .e("controller", "com.anhe3d.EclipsePattern1GenerateController")
                        )
                        .e(ApplicationModule.ECLIPSE_PATTERN_2_GROUP_NAME, map()
                                .e("model", "com.anhe3d.EclipsePattern2GenerateModel")
                                .e("view", "com.anhe3d.EclipsePattern2GenerateView")
                                .e("controller", "com.anhe3d.EclipsePattern2GenerateController")
                        )
                        .e(ApplicationModule.TUBE_PATTERN_GROUP_NAME, map()
                                .e("model", "com.anhe3d.TubePatternGenerateModel")
                                .e("view", "com.anhe3d.TubePatternGenerateView")
                                .e("controller", "com.anhe3d.TubePatternGenerateController")
                        )
                );
    }
}