package com.anhe3d.util;

import com.anhe3d.domain.IntersectionStructure;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.ext.awt.geom.Polygon2D;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FopFactory;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by kevinhung on 5/25/16.
 */
public class SvgImageGenerator {

    public enum TestSvgImageType {
        BOX, SPHERE, PYRAMID, PATH
    }

    final static String strPathCMYKProfile = "src/main/resources/icc-files/JapanWebCoated.icc";
    //    final static String strPathCMYKProfile = "src/main/resources/icc-files/CoatedFOGRA27.icc";
    ColorSpace instance;

    // Create an instance of org.w3c.dom.Document.
    String svgNS = "http://www.w3.org/2000/svg";

    // Get a DOMImplementation.
    DOMImplementation domImpl;

    Document document;

    // Create an instance of the SVG Generator.
    SVGGeneratorContext ctx;

    SVGGraphics2D svgGenerator;
    SVGConverter converter;
    FopFactory fopFactory;
    boolean useCSS = true; // we want to use CSS style attributes

    public SvgImageGenerator() throws IOException, SAXException {
        domImpl = GenericDOMImplementation.getDOMImplementation();
        document = domImpl.createDocument(svgNS, "svg", null);
//        int width = 50; //210
//        int height = 50; //297
//        int width = 210; //210
//        int height = 225; //297
        int width = 842;
        int height = 1191;

        ctx = SVGGeneratorContext.createDefault(document);
        ctx.setComment("Generated by Application with Batik SVG Generator");
        svgGenerator = new SVGGraphics2D(ctx, false);
        svgGenerator.setSVGCanvasSize(new Dimension(width, height)); // this contains no unit...


        converter = new SVGConverter();
        converter.setDestinationType(DestinationType.PDF);
        converter.setWidth(width);
        converter.setHeight(height);

        fopFactory = FopFactory.newInstance(FileUtils.getFile("src/main/resources/fop-conf.xml"));

        ICC_Profile profile = ICC_Profile.getInstance("src/main/resources/icc-files/CoatedFOGRA27.icc");
        instance = new ICC_ColorSpace(profile);
    }

    /**
     * Create Path2D for each layer of SA. When we use svgGenerator to fill the Path2D, the fill-rule will
     * automatically to be evenodd which provide us a great feature.
     */
    public void generateContour(String outputPath, int layer, java.util.List<LinkedList<IntersectionStructure>> resultIndexArray) throws IOException, SVGConverterException, TranscoderException {
        svgGenerator.setPaint(Color.red);


//        float[] cmyk = {0, 0, 0, 1};
//        Color color = new Color(instance, cmyk, 1);
//        System.out.println(color);


        Path2D path2D = new Path2D.Float();
        resultIndexArray.forEach(contour -> {
            if (contour.size() > 0) {
//                System.out.println(contour.size());
                float[] x = new float[contour.size()];
                float[] y = new float[contour.size()];
                for (int i = 0; i < contour.size(); i++) {
                    x[i] = (float) contour.get(i).getInterVertex().getCoord().x + 100;
                    y[i] = (float) contour.get(i).getInterVertex().getCoord().y + 50;
                }
                Polygon2D polygon2D = new Polygon2D(x, y, x.length);
                path2D.append(polygon2D, false);
            }
        });

        svgGenerator.fill(path2D);
//        svgGenerator.draw(path2D);

        Path output = Paths.get(String.format("%s/layer-%06d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    public void generateContour(File outputPath, int layer, java.util.List<LinkedList<IntersectionStructure>> resultIndexArray, Color color) throws IOException, SVGConverterException, TranscoderException {
        svgGenerator.setPaint(color);


//        float[] cmyk = {0, 0, 0, 1};
//        Color color = new Color(instance, cmyk, 1);
//        System.out.println(color);


        Path2D path2D = new Path2D.Float();
        resultIndexArray.forEach(contour -> {
            if (contour.size() > 0) {
//                System.out.println(contour.size());
                float[] x = new float[contour.size()];
                float[] y = new float[contour.size()];
                for (int i = 0; i < contour.size(); i++) {
                    x[i] = (float) contour.get(i).getInterVertex().getCoord().x + 150;
                    y[i] = (float) contour.get(i).getInterVertex().getCoord().y + 300;
                }
                Polygon2D polygon2D = new Polygon2D(x, y, x.length);
                path2D.append(polygon2D, false);
            }
        });

        svgGenerator.fill(path2D);
//        svgGenerator.draw(path2D);

        Path output = Paths.get(String.format("%s/layer-%06d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    public void generateColoredContour(String outputPath, int layer, java.util.List<LinkedList<IntersectionStructure>> resultIndexArray) throws IOException, SVGConverterException, TranscoderException {

        resultIndexArray.forEach(contour -> {
            BasicStroke strokeLine = new BasicStroke((float) 0.3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
            svgGenerator.setStroke(strokeLine);

            if (contour.size() > 0) {
                float[] x = new float[contour.size()];
                float[] y = new float[contour.size()];
                for (int i = 0; i < contour.size(); i++) {
//                    x[i] = (float) contour.get(i).getInterVertex().getCoord().x + 200;
//                    y[i] = (float) contour.get(i).getInterVertex().getCoord().y + 100;

                    if (i != 0) {
                        Line2D line2D = new Line2D.Float(
                                (float) contour.get(i - 1).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i - 1).getInterVertex().getCoord().y + 25,
                                (float) contour.get(i).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i).getInterVertex().getCoord().y + 25
                        );
//                        if (i>contour.size()/2) {
//                            svgGenerator.setPaint(Color.BLUE);
//                        } else {
//                            svgGenerator.setPaint(Color.red);
//                        }


                        svgGenerator.setPaint(contour.get(i - 1).getInterVertex().getColor().get());
                        svgGenerator.draw(line2D);
                    } else {
                        Line2D line2D = new Line2D.Float(
                                (float) contour.get(i).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i).getInterVertex().getCoord().y + 25,
                                (float) contour.get(contour.size() - 1).getInterVertex().getCoord().x + 25,
                                (float) contour.get(contour.size() - 1).getInterVertex().getCoord().y + 25
                        );
                        svgGenerator.setPaint(contour.get(contour.size() - 1).getInterVertex().getColor().get());
                        svgGenerator.draw(line2D);

                    }

                }

            }
        });

//        BasicStroke strokeLine  = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
//        Line2D line2D = new Line2D.Float(new Point2D.Float(0, 0), new Point2D.Float(100, 100));
//        svgGenerator.setPaint(Color.BLUE);
//        svgGenerator.setStroke(strokeLine);
////        svgGenerator.setUnsupportedAttributes();
//        svgGenerator.draw(line2D);
//        line2D = new Line2D.Float(new Point2D.Float(100, 100), new Point2D.Float(0, 100));
//        svgGenerator.setPaint(Color.CYAN);
//        svgGenerator.draw(line2D);
//        line2D = new Line2D.Float(new Point2D.Float(0, 100), new Point2D.Float(0, 0));
//        svgGenerator.setPaint(Color.GREEN);


        Path output = Paths.get(String.format("%s/layer-%06d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }


    public void generateColoredContour(File outputPath, int layer, java.util.List<LinkedList<IntersectionStructure>> resultIndexArray) throws IOException, SVGConverterException, TranscoderException {

        resultIndexArray.forEach(contour -> {
            BasicStroke strokeLine = new BasicStroke((float) 0.3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
            svgGenerator.setStroke(strokeLine);

            if (contour.size() > 0) {
                float[] x = new float[contour.size()];
                float[] y = new float[contour.size()];
                for (int i = 0; i < contour.size(); i++) {
//                    x[i] = (float) contour.get(i).getInterVertex().getCoord().x + 200;
//                    y[i] = (float) contour.get(i).getInterVertex().getCoord().y + 100;

                    if (i != 0) {
                        Line2D line2D = new Line2D.Float(
                                (float) contour.get(i - 1).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i - 1).getInterVertex().getCoord().y + 25,
                                (float) contour.get(i).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i).getInterVertex().getCoord().y + 25
                        );
//                        if (i>contour.size()/2) {
//                            svgGenerator.setPaint(Color.BLUE);
//                        } else {
//                            svgGenerator.setPaint(Color.red);
//                        }


                        svgGenerator.setPaint(contour.get(i - 1).getInterVertex().getColor().get());
                        svgGenerator.draw(line2D);
                    } else {
                        Line2D line2D = new Line2D.Float(
                                (float) contour.get(i).getInterVertex().getCoord().x + 25,
                                (float) contour.get(i).getInterVertex().getCoord().y + 25,
                                (float) contour.get(contour.size() - 1).getInterVertex().getCoord().x + 25,
                                (float) contour.get(contour.size() - 1).getInterVertex().getCoord().y + 25
                        );
                        svgGenerator.setPaint(contour.get(contour.size() - 1).getInterVertex().getColor().get());
                        svgGenerator.draw(line2D);

                    }

                }

            }
        });

//        BasicStroke strokeLine  = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
//        Line2D line2D = new Line2D.Float(new Point2D.Float(0, 0), new Point2D.Float(100, 100));
//        svgGenerator.setPaint(Color.BLUE);
//        svgGenerator.setStroke(strokeLine);
////        svgGenerator.setUnsupportedAttributes();
//        svgGenerator.draw(line2D);
//        line2D = new Line2D.Float(new Point2D.Float(100, 100), new Point2D.Float(0, 100));
//        svgGenerator.setPaint(Color.CYAN);
//        svgGenerator.draw(line2D);
//        line2D = new Line2D.Float(new Point2D.Float(0, 100), new Point2D.Float(0, 0));
//        svgGenerator.setPaint(Color.GREEN);


        Path output = Paths.get(String.format("%s/layer-%06d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    public void exportPDF(String inputPath, String outputPath) throws SVGConverterException, IOException { // this could not export all svg files in single pdf
        String[] collect = FileUtils.listFiles(Paths.get(inputPath).toFile(), new String[]{"svg"}, false)
                .stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList())
                .toArray(new String[]{});
        converter.setSources(collect);
        converter.setDst(Paths.get(outputPath).toFile());
        converter.execute();
    }

    public void exportPNG(String inputPath, String outputPath) throws IOException, TranscoderException {
        FileOutputStream outputStream = new FileOutputStream(new File(outputPath + ".png"));
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(Paths.get(inputPath).toUri().toString());
//            for (int i = 1; i <=map.size()/3; i++) {
//                Element e = doc.getElementById(map.get("id"+i));
//                System.out.println(map.get("name"+i));
//                e.setAttribute(map.get("name"+i), map.get("value"+i));
//            }
        PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(doc);
        TranscoderOutput output = new TranscoderOutput(outputStream);
        t.transcode(input, output);


    }

    //Be ware of file name index in generateContour() ("%s/layer-%03d.svg" -> "%s/layer-%06d.svg")
    public void mergePDF(String inputPath, String outputPath) throws IOException {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        FileUtils.listFiles(Paths.get(inputPath).toFile(), new String[]{"pdf"}, false)
                .stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList())
                .forEach((source) -> {
                    try {
                        System.out.println(source);
                        mergePdf.addSource(source);
                    } catch (FileNotFoundException ignored) {
                    }
                });
        mergePdf.setDestinationFileName(outputPath);
        mergePdf.mergeDocuments(null);
    }

    private static String[] getFiles(String folder) throws IOException {
        File _folder = new File(folder);
        String[] filesInFolder;
        if (_folder.isDirectory()) {
            filesInFolder = _folder.list();
            return filesInFolder;
        } else {
            throw new IOException("Path is not a directory");
        }
    }

    public void generate(String outputPath, TestSvgImageType type) throws IOException {
        FileUtils.deleteDirectory(Paths.get(outputPath).toFile());

        switch (type) {
            case BOX:
                for (int i = 1; i <= 100; i++) {
                    generateBox(outputPath, i);
                }
                break;
            case SPHERE:
                for (int i = 1; i <= 100; i++) {
                    generateSphere(outputPath, i);
                }
                break;
            case PYRAMID:
                for (int i = 1; i <= 100; i++) {
                    generatePyramid(outputPath, i);
                }
                break;
        }
    }

    public void generateCMYK(String outputPath, int layer) throws IOException {

        float alpha = (float) 1;

//        LinearGradientPaint linearGradientPaint = new LinearGradientPaint();

        for (int i = 25; i < 225; i += 8) {
            Rectangle rectangleC = new Rectangle(0, i, 25, 8);
            float[] cmyk1 = {(float) (i - 25) / 200, 0, 0, 0};
            Color color1 = new Color(instance, cmyk1, alpha);
            svgGenerator.setPaint(color1);
            svgGenerator.fill(rectangleC);

            Rectangle rectangleM = new Rectangle(25, i, 25, 8);
            float[] cmyk2 = {0, (float) (i - 25) / 200, 0, 0};
            Color color2 = new Color(instance, cmyk2, alpha);
            svgGenerator.setPaint(color2);
            svgGenerator.fill(rectangleM);

            Rectangle rectangleY = new Rectangle(50, i, 25, 8);
            float[] cmyk3 = {0, 0, (float) (i - 25) / 200, 0};
            Color color3 = new Color(instance, cmyk3, alpha);
            svgGenerator.setPaint(color3);
            svgGenerator.fill(rectangleY);

            Rectangle rectangleK = new Rectangle(75, i, 25, 8);
            float[] cmyk4 = {0, 0, 0, (float) (i - 25) / 200};
            Color color4 = new Color(instance, cmyk4, alpha);
            svgGenerator.setPaint(color4);
            svgGenerator.fill(rectangleK);
        }


        Path output = Paths.get(String.format("%s/layer-%03d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    public void generateBox(String outputPath, int layer) throws IOException {
        svgGenerator.setPaint(Color.red);

        if (layer > 25 && layer <= 75) {
            Rectangle rectangle = new Rectangle(10, 10, 100, 100);
            svgGenerator.fill(rectangle);
        }
        Path output = Paths.get(String.format("%s/layer-%03d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    void generateSphere(String outputPath, int layer) throws IOException {
        svgGenerator.setPaint(Color.red);

        int r = Math.abs(layer > 50 ? 100 - layer : layer);
        double center = (100 - r) / 2;
        Ellipse2D.Double circle = new Ellipse2D.Double(center, center, r, r);

        svgGenerator.fill(circle);

        Path output = Paths.get(String.format("%s/svg/layer-%03d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }

    void generatePyramid(String outputPath, int layer) throws IOException {
        svgGenerator.setPaint(Color.red);

        double center = (100 - layer) / 2;
        Rectangle.Double rectangle = new Rectangle.Double(center, center, layer, layer);
        svgGenerator.fill(rectangle);

        Path output = Paths.get(String.format("%s/svg/layer-%03d.svg", outputPath, layer));
        FileOutputStream outputStream = FileUtils.openOutputStream(output.toFile());
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, useCSS);
    }


    public static float[] cmyk2rgb(float... cmyk) throws IOException {
        if (cmyk.length != 4) {
            throw new IllegalArgumentException();
        }

        ColorSpace instance = new ICC_ColorSpace(ICC_Profile.getInstance(strPathCMYKProfile));
        return instance.toRGB(cmyk);
    }

    public static float[] rgb2cmyk(float... rgb) throws IOException {
        if (rgb.length != 3) {
            throw new IllegalArgumentException();
        }

        ColorSpace instance = new ICC_ColorSpace(ICC_Profile.getInstance(strPathCMYKProfile));
        return instance.fromRGB(rgb);
    }
}
