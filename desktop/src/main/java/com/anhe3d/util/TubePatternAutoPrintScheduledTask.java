package com.anhe3d.util;

import com.anhe3d.CommProtocol;
import com.anhe3d.SerialService;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by jimmy on 2016/12/21.
 */
@Slf4j
public class TubePatternAutoPrintScheduledTask extends TimerTask {

    private long counter;
    private final long counterLimit;
    private final SerialService serialService;
    private List<File> svgFiles;
    private SvgImageGenerator imageGenerator;

    @Inject
    public TubePatternAutoPrintScheduledTask(SerialService serialService, File folderPath) throws IOException, SAXException {
        imageGenerator = new SvgImageGenerator();
        counter = 0;
        svgFiles = (List<File>) FileUtils.listFiles(folderPath, new String[]{"svg"}, false);
        this.counterLimit = svgFiles.size();
        this.serialService = serialService;
    }

    @Override
    public void run() {
        if (counterLimit == counter) {
            this.cancel();
        } else {
            log.info("Task " + counter + " is Executing...");
        }
        if (serialService.isPortOpened()) {
            File svgFile = svgFiles.get((int) counter++);
            try {
                imageGenerator.exportPNG(svgFile.getAbsolutePath(), svgFile.getAbsolutePath().replace(".svg", ""));
            } catch (IOException | TranscoderException e) {
                e.printStackTrace();
            }
            File pngFile = FileUtils.getFile(svgFile.getAbsolutePath().replace(".svg", ".png"));
            if (null != pngFile) {
                PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                pras.add(new Copies(1));
                pras.add(MediaSizeName.ISO_A3);
                pras.add(OrientationRequested.PORTRAIT);
                PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, pras);
                if (pss.length == 0)
                    throw new RuntimeException("No printer services available.");
                PrintService ps = pss[0];
                log.info("Printing to " + ps);
                DocPrintJob job = ps.createPrintJob();
                Doc doc = null;
                DocAttributeSet das = new HashDocAttributeSet();
                das.add(new PrinterResolution(2880, 2880, ResolutionSyntax.DPI));
                das.add(PrintQuality.HIGH);
                try {
                    doc = new SimpleDoc(FileUtils.openInputStream(pngFile), DocFlavor.INPUT_STREAM.PNG, das);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    job.print(doc, pras);
                } catch (PrintException e) {
                    e.printStackTrace();
                }
                serialService.write(CommProtocol.POWDER_START);
            }
        }
    }
}
