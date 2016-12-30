package com.anhe3d;

import com.anhe3d.domain.Facet;
import com.anhe3d.util.SliceAlgorithm;
import com.anhe3d.util.StlLoader;
import com.anhe3d.util.TubePatternAutoPrintScheduledTask;
import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.print.PrintException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@ArtifactProviderFor(GriffonController.class)
@Slf4j
public class TubePatternGenerateController extends AbstractGriffonController {

    @MVCMember
    @Nonnull
    private TubePatternGenerateModel model;

    @MVCMember
    @Nonnull
    private TubePatternGenerateView view;

    @Inject
    private SerialService serialService;

    @Inject
    private PrintService printService;

    private File outputFilePath;

    private Timer timer;
    private TubePatternAutoPrintScheduledTask task;

    @MVCMember
    public void setModel(@Nonnull TubePatternGenerateModel model) {
        this.model = model;
    }

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        timer = new Timer();
    }

    @Override
    public void mvcGroupDestroy() {
        timer.purge();
        timer.cancel();
        super.mvcGroupDestroy();
    }

    @Threading(Threading.Policy.SKIP)
    public void saveFolder() throws IOException, SAXException, TranscoderException, SVGConverterException {
        File inputFile = view.selectReadFile();
        outputFilePath = view.selectFileFolderPath();

        if (null != outputFilePath && null != inputFile) {
            FileUtils.cleanDirectory(outputFilePath);

            InputStream inputStream = FileUtils.openInputStream(inputFile);
            String header = StlLoader.loadBinaryHeader(inputStream);
            long facetCount = StlLoader.loadBinaryFacetCount(inputStream);
            List<Facet> facets = StlLoader.loadBinaryContent(inputStream, facetCount);
            log.info("File Header: " + header);
            log.info("Facet count: " + facetCount);

            SliceAlgorithm.processAlgorithm(0.5, facets);// 0.05

            SliceAlgorithm.generateSvgContour(outputFilePath, model.getColor());
        }
    }

    @Threading(Threading.Policy.SKIP)
    public void load() throws IOException {
        File loadFilePath = view.selectReadFile();
        if (null != loadFilePath) {
            FileInputStream imageData = FileUtils.openInputStream(loadFilePath);
            Image image = new Image(imageData);
            runInsideUISync(() -> {
                view.setImageView(image);
            });
        }
    }

    @Threading(Threading.Policy.SKIP)
    public void print() throws TranscoderException, SAXException, PrintException, IOException {
        if (null != outputFilePath) {
            log.info("Print Started");
            task = new TubePatternAutoPrintScheduledTask(serialService, outputFilePath);
            timer.scheduleAtFixedRate(task, 0, 60000);
        } else {
            if (serialService.isPortOpened()) {
                log.info("Print Single");
                runInsideUIAsync(() -> {
                    printService.print(view.getImageView());
                });
                serialService.write(CommProtocol.POWDER_START);
            }
        }
    }
}