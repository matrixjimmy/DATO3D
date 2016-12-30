package com.anhe3d.util;

import com.anhe3d.domain.*;
import com.anhe3d.domain.javax.vecmath.Color4b;
import com.anhe3d.domain.javax.vecmath.Point3d;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * Created by kevinhung on 5/30/16.
 * An algorithm for slicing model with color information.
 * Usage flow: processAlgorithm -> generateSvgContour
 */

public class SliceAlgorithm {
    private static double Z_INCREMENT;
    private static double modelZMax;
    private static double modelZMin;
    private static double modelXMax;
    private static double modelXMin;
    private static double modelYMax;
    private static double modelYMin;
    private static double sTotal;
    private static List<Facet> facets;
    private static List<LinkedList> sliceArrays;
    private static SvgImageGenerator svgImageGenerator;

    /**
     * Process the actual algorithm of Zhang 2015
     */
    public static void processAlgorithm(double sliceThickness, List<Facet> facets) throws IOException {
        Z_INCREMENT = sliceThickness;
        SliceAlgorithm.facets = facets;

        preprocessModel();
        processOverFacets(sliceArrays);
    }

    /**
     * Preprocess the model for getting the data of modelZMax, modelZMin, sTotal and sliceArrays
     * modelZMax and modelZMax: maximum and minimum height of model
     * sTotal: the total unit Z_INCREMENT that going to be slice
     * sliceArrays: contains all CLL and ILL for each layer and each contour
     */
    private static void preprocessModel() {
        modelZMax = -Double.MAX_VALUE;
        modelZMin = Double.MAX_VALUE;
        modelXMax = -Double.MAX_VALUE;
        modelXMin = Double.MAX_VALUE;
        modelYMax = -Double.MAX_VALUE;
        modelYMin = Double.MAX_VALUE;

        for (Facet facet : facets) {
            double facetZMin = facet.getEdges()[0].getZMin();
            double facetZMax = facet.getEdges()[0].getZMax();
            double facetXMin = facet.getEdges()[0].getXMin();
            double facetXMax = facet.getEdges()[0].getXMax();
            double facetYMin = facet.getEdges()[0].getYMin();
            double facetYMax = facet.getEdges()[0].getYMax();


            modelZMax = facetZMax > modelZMax ? facetZMax : modelZMax;
            modelZMin = facetZMin < modelZMin ? facetZMin : modelZMin;
            modelXMax = facetXMax > modelXMax ? facetXMax : modelXMax;
            modelXMin = facetXMin < modelXMin ? facetXMin : modelXMin;
            modelYMax = facetYMax > modelYMax ? facetYMax : modelYMax;
            modelYMin = facetYMin < modelYMin ? facetYMin : modelYMin;
        }

        sTotal = (modelZMax - modelZMin) / Z_INCREMENT;
        sliceArrays = new ArrayList<>((int) sTotal + 1);
        int counter = 0;
        while (counter++ < (int) sTotal + 1) {
            sliceArrays.add(new LinkedList<>());
        }

        System.out.println("********************************************************");
        System.out.println("Model Min Z: " + modelZMin + "; Model Max Z: " + modelZMax +
                "\nModel Min X: " + modelXMin + "; Model Max X: " + modelXMax +
                "\nModel Min Y: " + modelYMin + "; Model Max Y: " + modelYMax +
                "\nFacets Count: " + facets.size() + "\nsTotal: " + sTotal);
        System.out.println("********************************************************");
    }

    /**
     * From Section 4.2 to 4.4, doing it twice for S1 & S2 and S1 & S3 edges
     * sMin, sMed and sMax need to be handle carefully. Since unit index of height
     * must be integer which is form 0 to (int)sTotal + 1
     */
    private static void processOverFacets(List<LinkedList> sliceArrays) {

        for (Facet facet : facets) {
            double facetZMin = facet.getEdges()[0].getZMin();
            double facetZMax = facet.getEdges()[0].getZMax();
            double facetZMed = facet.getEdges()[1].getZMax();

            if (facetZMin != facetZMax) {
                //Section 4.2
                //Calculate the unit height we are going to process
                double sMin = (facetZMin - modelZMin) / Z_INCREMENT;
                double sMed = (facetZMed - modelZMin) / Z_INCREMENT;
                double sMax = (facetZMax - modelZMin) / Z_INCREMENT;

                //Section 4.3
                EdgePair edgePair;
                Edge s1Edge = facet.getEdges()[0];
                Edge s2Edge = facet.getEdges()[1];
                Edge s3Edge = facet.getEdges()[2];
                if (s1Edge.getVertexes()[0].getCoord().getZ() > s1Edge.getVertexes()[1].getCoord().getZ()) {
                    edgePair = new EdgePair(s1Edge, s2Edge);
                } else {
                    edgePair = new EdgePair(s2Edge, s1Edge);
                }

                //Section 4.4
                //Solve interVertex and slicing plane and get IntersectionStructure
                int j = sMin > 0 ? (int) sMin + 1 : 0;
                int temp = (sMed > 0) ? (int) sMed + 1 : 0;
                while (j < temp) {

                    //Construct IS
                    Edge interEdge = edgePair.getForwardEdge();
                    IntersectionStructure IS = new IntersectionStructure(
                            computeIntersectionVertex(facetZMed, interEdge, j, sMin, sMed, sMed),
                            edgePair
                    );
                    //Construct contour list
                    constructContourList(IS, sliceArrays, j);
                    j++;
                }

                //Section 4.3
                //Determine S1 and S3 for judging forward or backward edge
                if (s1Edge.getVertexes()[0].getCoord().getZ() > s1Edge.getVertexes()[1].getCoord().getZ()) {
                    edgePair = new EdgePair(s1Edge, s3Edge);
                } else {
                    edgePair = new EdgePair(s3Edge, s1Edge);
                }

                //Section 4.4
                //Solve interVertex and slicing plane and get IntersectionStructure
                int k = sMed > 0 ? (int) sMed + 1 : 0;
                //Testing j-k should be 1 or 0, 1 is normal condition and 0 is with condition of sMin = SMed
//                if ((j-k) == 1) { System.out.println("@@ "+ (j-k)); }
                while (k < (int) sMax + 1) {
                    Edge interEdge = edgePair.getForwardEdge();
                    IntersectionStructure IS = new IntersectionStructure(
                            computeIntersectionVertex(facetZMed, interEdge, k, sMed, sMax, sMed),
                            edgePair
                    );
                    //Construct contour list
                    constructContourList(IS, sliceArrays, k);
                    k++;
                }
            }
        }
    }

    /**
     * Generate the Contour form each layer of SA and take advantage of SvgImageGenerator
     * to get the svg file. Before generate the svg file, we have to clean the directory and
     * then store the new generated svg in it.
     */
    public static void generateSvgContour(String outputPath) throws IOException, SAXException, SVGConverterException, TranscoderException {
        FileUtils.deleteDirectory(Paths.get(outputPath).toFile());
        svgImageGenerator = new SvgImageGenerator();

        int indexLayer = 0;
        while (indexLayer++ < sliceArrays.size() - 1) {
            List<LinkedList<IntersectionStructure>> resultIndexArray = sliceArrays.get(indexLayer);
            svgImageGenerator.generateContour("svg-output-files/svg", indexLayer, resultIndexArray);
        }
    }

    public static void generateSvgContour(File outputPath, Color color) throws IOException, SAXException, SVGConverterException, TranscoderException {
        svgImageGenerator = new SvgImageGenerator();
        //FileUtils.deleteDirectory(Paths.get(outputPath).toFile());

        int indexLayer = 0;
        while (indexLayer++ < sliceArrays.size() - 1) {
            List<LinkedList<IntersectionStructure>> resultIndexArray = sliceArrays.get(indexLayer);
            svgImageGenerator.generateContour(outputPath, indexLayer, resultIndexArray, color);
        }
    }

    public static void generateSvgColoredContour(String outputPath) throws IOException, SAXException, SVGConverterException, TranscoderException {
        FileUtils.deleteDirectory(Paths.get(outputPath).toFile());
        svgImageGenerator = new SvgImageGenerator();

        int indexLayer = 0;
        while (indexLayer++ < sliceArrays.size() - 1) {
            List<LinkedList<IntersectionStructure>> resultIndexArray = sliceArrays.get(indexLayer);
            svgImageGenerator.generateColoredContour("svg-output-files/svg", indexLayer, resultIndexArray);
        }
    }

    public static void generateSvgColoredContour(File outputPath, Color4b color) throws IOException, SAXException, SVGConverterException, TranscoderException {
        svgImageGenerator = new SvgImageGenerator();
        //FileUtils.deleteDirectory(outputPath);

        int indexLayer = 0;
        while (indexLayer++ < sliceArrays.size() - 1) {
            List<LinkedList<IntersectionStructure>> resultIndexArray = sliceArrays.get(indexLayer);
            resultIndexArray.forEach(structures -> structures.parallelStream()
                    .forEach(intersectionStructure -> intersectionStructure.getInterVertex().setColor(color)));
            svgImageGenerator.generateColoredContour(outputPath, indexLayer, resultIndexArray);
        }
    }

    /**
     * Get the intersection point by the portion of unit index. There are four kinds of intersection condition.
     * If S1 is not the forward edge, intersection is easy to get by S2's min & max height and S3's min & max height.
     * But if S1 is the forward edge, need to deal with it very carefully.
     */
    private static Vertex computeIntersectionVertex(double facetZMed, Edge interEdge, int sCounter, double sLower, double sUpper, double sMedium) {
        double sRange = sUpper - sLower;
        Vertex interVertex = new Vertex();

        //TODO modify to be more efficient
        Point3d point1 = interEdge.getVertexes()[0].getCoord().z > interEdge.getVertexes()[1].getCoord().z
                ? interEdge.getVertexes()[0].getCoord() : interEdge.getVertexes()[1].getCoord();
        Point3d point2 = interEdge.getVertexes()[0].getCoord().z < interEdge.getVertexes()[1].getCoord().z
                ? interEdge.getVertexes()[0].getCoord() : interEdge.getVertexes()[1].getCoord();

        if (point1.z != facetZMed && point2.z != facetZMed && sCounter < (int) sMedium + 1) {
            Point3d pointMed = interEdge.getPoint3dByHeight(facetZMed);
            interVertex.setCoord(getIntersectionPoint(point2, pointMed, sCounter, sLower, sRange));
            interVertex.setColor(getIntersectionColor(interEdge.getVertexes()[1]));
//            System.out.println(getIntersectionColor(interEdge.getVertexes()[1]));
        } else if (point1.z != facetZMed && point2.z != facetZMed && sCounter >= (int) sMedium + 1) {
            Point3d pointMed = interEdge.getPoint3dByHeight(facetZMed);
            interVertex.setCoord(getIntersectionPoint(pointMed, point1, sCounter, sLower, sRange));
            interVertex.setColor(getIntersectionColor(interEdge.getVertexes()[1]));
//            System.out.println(getIntersectionColor(interEdge.getVertexes()[1]));
        } else {
            interVertex.setCoord(getIntersectionPoint(point2, point1, sCounter, sLower, sRange));
            interVertex.setColor(getIntersectionColor(interEdge.getVertexes()[1]));
//            System.out.println(getIntersectionColor(interEdge.getVertexes()[1]));

        }
        return interVertex;
    }

    private static Point3d getIntersectionPoint(Point3d pointLow, Point3d pointHigh, int sCounter, double sLower, double sRange) {
        double x = pointLow.x + ((sCounter - sLower) * (pointHigh.x - pointLow.x) / sRange);
        double y = pointLow.y + ((sCounter - sLower) * (pointHigh.y - pointLow.y) / sRange);
        double z = pointLow.z + ((sCounter - sLower) * (pointHigh.z - pointLow.z) / sRange);
        return new Point3d(x, y, z);
    }

    /**
     * Temp setting of color for a facet one color. Not depend on vertex
     */
    private static Color4b getIntersectionColor(Vertex v1) {
        return v1.getColor();
    }

    private static void constructContourList(IntersectionStructure IS, List<LinkedList> sliceArrays, int k) {
        List<LinkedList<IntersectionStructure>> sliceArray = sliceArrays.get(k);
        if (sliceArray.size() == 0) {
            LinkedList<IntersectionStructure> newContourList = new LinkedList<>();
            newContourList.add(IS);
            sliceArray.add(newContourList);
        } else {
            Map<String, LinkedList<IntersectionStructure>> attachedContourList = new HashMap<>();
            for (LinkedList<IntersectionStructure> contourList : sliceArray) {
                IntersectionStructure headIS = contourList.getFirst();
                IntersectionStructure tailIS = contourList.getLast();

                //Check whether it should be the current contourList's head or tail
                //Has already eliminated the one contourList with head and back situation
                //
                if (IS.getEdgePair().getBackwardEdge().isEqual(headIS.getEdgePair().getForwardEdge())) {
                    attachedContourList.put("headOfContour", contourList);
                } else if (IS.getEdgePair().getForwardEdge().isEqual(tailIS.getEdgePair().getBackwardEdge())) {
                    attachedContourList.put("backOfContour", contourList);
                }
            }
            //According to the size of attachedContourList
            if (attachedContourList.size() == 2) {
                attachedContourList.get("headOfContour").addFirst(IS);
                attachedContourList.get("backOfContour").addAll(attachedContourList.get("headOfContour"));
                sliceArray.remove(attachedContourList.get("headOfContour"));
            } else if (attachedContourList.size() == 1) {
                if (attachedContourList.get("headOfContour") != null) {
                    if (!attachedContourList.get("headOfContour").getFirst().getInterVertex().isEqualZ(IS.getInterVertex())) {
                    }
                    attachedContourList.get("headOfContour").addFirst(IS);
                } else if (attachedContourList.get("backOfContour") != null) {
                    if (!attachedContourList.get("backOfContour").getFirst().getInterVertex().isEqualZ(IS.getInterVertex())) {
                    }
                    attachedContourList.get("backOfContour").addLast(IS);
                }
            } else if (attachedContourList.size() == 0) {
                LinkedList<IntersectionStructure> newContourList = new LinkedList<>();
                newContourList.add(IS);
                sliceArray.add(newContourList);
            } else {
                System.out.println("Something Wrong with you attachedContourList");
            }
        }
    }

}
