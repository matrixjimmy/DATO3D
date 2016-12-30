package com.anhe3d;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import javafx.print.*;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jimmy on 2016/12/16.
 */
@Singleton
@ArtifactProviderFor(GriffonService.class)
@Slf4j
public class PrintService extends AbstractGriffonService {

    @Getter
    @Setter
    private Printer printer;

    private PageLayout defaultPageLayout;

    private List<PrinterJob> jobs;

    public PrintService() {
        this.printer = Printer.getDefaultPrinter();
        defaultPageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL_OPPOSITES);
        jobs = new LinkedList<>();
    }

    public boolean isPrinterBusy() {
        return jobs.stream().filter(job -> job.getJobStatus().equals(PrinterJob.JobStatus.PRINTING)).count() > 0;
    }

    PrinterJob getNewJobInstance() throws GetPrinterJobException {
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        job.getJobSettings().setPrintQuality(PrintQuality.HIGH);
        PrintResolution printResolution = job.getJobSettings().getPrintResolution();
        try {
            BeanUtils.setProperty(printResolution, "cfRes", 600);
            BeanUtils.setProperty(printResolution, "fRes", 600);
            System.out.println(printResolution.getCrossFeedResolution() + "x" + printResolution.getFeedResolution());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        job.getJobSettings().setPrintResolution(printResolution);
        jobs.add(job);
        if (job != null) {
            return job;
        }
        throw new GetPrinterJobException();
    }

    public void print(Node node) {
        try {
            PrinterJob job = getNewJobInstance();
            boolean success = job.printPage(defaultPageLayout, node);
            if (success) {
                job.endJob();
            }
        } catch (GetPrinterJobException e) {
            e.printStackTrace();
        }
    }

    private class GetPrinterJobException extends Throwable {
    }
}
