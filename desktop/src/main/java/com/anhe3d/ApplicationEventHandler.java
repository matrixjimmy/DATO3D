package com.anhe3d;

import griffon.core.GriffonApplication;
import griffon.core.event.EventHandler;
import jssc.SerialPortException;

import javax.inject.Inject;

public class ApplicationEventHandler implements EventHandler {
    /*
    public void onBootstrapStart(GriffonApplication application) {

    }
    */
    @Inject
    private SerialService serialService;

    public void onShutdown(GriffonApplication application) throws SerialPortException {
        if (serialService.isPortOpened()) {
            System.out.println("Shutdown Serial Port");
            serialService.closePort();
        }
    }
}