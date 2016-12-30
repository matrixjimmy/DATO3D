package com.anhe3d;

import griffon.core.event.EventHandler;
import griffon.core.injection.Module;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.kordamp.jipsy.ServiceProviderFor;

@ServiceProviderFor(Module.class)
public class ApplicationModule extends AbstractModule {

    public static String PILL_PATTERN_GROUP_NAME = "pill-pattern-generate";
    public static String PILL_PATTERN_GROUP_TABNAME = "Generate Pill";

    public static String ECLIPSE_PATTERN_1_GROUP_NAME = "eclipse-pattern-1-generate";
    public static String ECLIPSE_PATTERN_1_GROUP_TABNAME = "Generate Pattern 1";

    public static String ECLIPSE_PATTERN_2_GROUP_NAME = "eclipse-pattern-2-generate";
    public static String ECLIPSE_PATTERN_2_GROUP_TABNAME = "Generate Pattern 2";

    public static String TUBE_PATTERN_GROUP_NAME = "tube-pattern-generate";
    public static String TUBE_PATTERN_GROUP_TABNAME = "Generate Tube Pattern";

    @Override
    protected void doConfigure() {
        bind(EventHandler.class)
                .to(ApplicationEventHandler.class)
                .asSingleton();
    }
}