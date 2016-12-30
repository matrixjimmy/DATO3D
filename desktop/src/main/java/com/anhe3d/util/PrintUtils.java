package com.anhe3d.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by jimmy on 2016/12/21.
 */
@Slf4j
public class PrintUtils {
    public static void main(String[] args) {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
//        pras.add(new PrinterResolution(72, 72, ResolutionSyntax.DPI));
        pras.add(MediaSizeName.ISO_A3);
        pras.add(OrientationRequested.PORTRAIT);
        PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, pras);
        if (pss.length == 0)
            throw new RuntimeException("No printer services available.");
        for (PrintService ps : pss) {
            log.info(ps.getName());
        }
        PrintService ps = pss[0];
        log.info("Printing to " + ps);
        DocPrintJob job = ps.createPrintJob();
        Doc doc = null;
        DocAttributeSet das = new HashDocAttributeSet();
        das.add(new PrinterResolution(2880, 2880, ResolutionSyntax.DPI));
        das.add(PrintQuality.HIGH);
//        das.add(new MediaPrintableArea(0,0,210000,297000, MediaPrintableArea.MM));
        try {
            doc = new SimpleDoc(FileUtils.openInputStream(new File("/Users/jimmy/Downloads/T/layer-000002.png")), DocFlavor.INPUT_STREAM.PNG, das);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            job.print(doc, pras);
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
}
