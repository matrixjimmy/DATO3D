package com.anhe3d.util;

import com.anhe3d.domain.Facet;
import com.anhe3d.domain.Vertex;
import com.anhe3d.domain.javax.vecmath.Color4b;
import com.anhe3d.domain.javax.vecmath.Vector3d;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jimmy on 2015/10/12.
 */
public class StlLoader {

    public static boolean isBinary(Path filePath) throws IOException {
        FileInputStream inputStream = FileUtils.openInputStream(filePath.toFile());
        String first80Bytes = new String(IOUtils.toByteArray(inputStream, 80));
        return !StringUtils.startsWithAny(first80Bytes, "solid ") && !StringUtils.contains(first80Bytes, "facet ");
    }

    public static boolean isBinary(byte[] fileContent) {
        String first80Bytes = new String(Arrays.copyOfRange(fileContent, 0, 80));
        return !StringUtils.startsWithAny(first80Bytes, "solid ");
    }

    public static List<String> loadAscii(Path filePath) throws IOException {
        FileInputStream inputStream = FileUtils.openInputStream(filePath.toFile());
        return IOUtils.readLines(inputStream);
    }

    public static String loadBinaryHeader(InputStream inputStream) throws IOException {
        byte[] header = new byte[80];
        if (80 != inputStream.read(header, 0, 80)) {
            throw new RuntimeException("Format Error: 80 bytes expected");
        }
        return StringUtils.trimToEmpty(new String(header));
    }

    static String loadBinaryHeader(byte[] bytes) {
        byte[] header = Arrays.copyOfRange(bytes, 0, 80);
        return StringUtils.trimToEmpty(new String(header));
    }

    public static int loadBinaryFacetCount(InputStream inputStream) throws IOException {
        byte[] facetCount = new byte[4];
        if (4 != inputStream.read(facetCount)) {
            throw new RuntimeException("Format Error: 4 bytes expected");
        }
        return ByteBuffer.wrap(facetCount).order(ByteOrder.nativeOrder()).getInt();
    }

    static int loadBinaryFacetCount(byte[] bytes) {
        byte[] facetCount = Arrays.copyOfRange(bytes, 81, 84);
        return ByteBuffer.wrap(facetCount).order(ByteOrder.nativeOrder()).getInt();
    }

    public static List<Facet> loadBinaryContent(InputStream inputStream, long facetCount) throws IOException {
        List<Facet> rtn = new LinkedList<>();
        boolean parseAttr = true;
        for (int i = 0; i < facetCount; i++) {
            if (inputStream.available() > 0) {
                byte[] byteArray = new byte[50];
                inputStream.read(byteArray);
                Facet e = loadSingleBinaryContent(byteArray, parseAttr);
                rtn.add(e);
                if (null == e.getVertexes()[0].getColor()) {
                    parseAttr = false;
                }
            }
        }
        return rtn;
    }

    public static List<Facet> loadBinaryContent(byte[] bytes) {
//        String header = loadBinaryHeader(bytes);
        int facetCount = loadBinaryFacetCount(bytes);
        bytes = Arrays.copyOfRange(bytes, 84, bytes.length);
        return loadBinaryContent(bytes, facetCount);
    }

    static List<Facet> loadBinaryContent(byte[] bytes, int facetCount) {
        List<Facet> rtn = new LinkedList<>();
        boolean parseAttr = true;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.nativeOrder());
        for (int i = 0; i < facetCount; i++) {
            if (bytes.length < 50) {
                throw new RuntimeException("facet count is wrong");
            }
            byte[] byteArray = new byte[50];
            byteBuffer.get(byteArray);
            Facet e = loadSingleBinaryContent(byteArray, parseAttr);
            rtn.add(e);
            if (null == e.getVertexes()[0].getColor()) {
                parseAttr = false;
            }
        }
        return rtn;
    }

    public static Facet loadSingleBinaryContent(byte[] byteArray, boolean parseAttr) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray).order(ByteOrder.nativeOrder());
        Vector3d normal = parseBinaryNormal(byteBuffer);
        Facet facet = new Facet(parseBinaryVertex(byteBuffer));
        facet.setNormal(normal);
        if (parseAttr) {
            Color4b color4b = parseBinaryAttribute(byteBuffer);
            facet.getVertexes()[0].setColor(color4b);
            facet.getVertexes()[1].setColor(color4b);
            facet.getVertexes()[2].setColor(color4b);
        } else {
            // Ignore attribute
            byteBuffer.getShort();
        }
        return facet;
    }

    private static Vector3d parseBinaryNormal(ByteBuffer normal) {
        Vector3d rtn = new Vector3d();
        rtn.x = normal.getFloat();
        rtn.y = normal.getFloat();
        rtn.z = normal.getFloat();
        return rtn;
    }

    private static Vertex[] parseBinaryVertex(ByteBuffer vertex) {
        Vertex p1 = new Vertex();
        Vertex p2 = new Vertex();
        Vertex p3 = new Vertex();

        p1.getCoord().x = vertex.getFloat();
        p1.getCoord().y = vertex.getFloat();
        p1.getCoord().z = vertex.getFloat();

        p2.getCoord().x = vertex.getFloat();
        p2.getCoord().y = vertex.getFloat();
        p2.getCoord().z = vertex.getFloat();

        p3.getCoord().x = vertex.getFloat();
        p3.getCoord().y = vertex.getFloat();
        p3.getCoord().z = vertex.getFloat();


        return new Vertex[]{p1, p2, p3};
    }

    private static Color4b parseBinaryAttribute(ByteBuffer attr) {

        byte x = attr.get();
        byte y = attr.get();
        byte r,g,b;
        r = (byte) (x & 0b01_111100 >> 2);
        g = (byte) (y & 0b1110_0000 >> 5 | x & 0b0001_0000 << 3);
        b = (byte) (y & 0b0001_1111);

//        System.out.print(String.format("Input: %o %o\n", x,y));
//        System.out.print(String.format("Output:%o %o %o\n", r,g,b));

//        if (aShort == 0) {
//            return null;
//        }


//        String attribute = Integer.toBinaryString(aShort).substring(Integer.SIZE - Short.SIZE);

        Color4b color = new Color4b();
        color.x = r;
        color.y = g;
        color.z = b;
        color.w = 127;
        return color;
    }

    public static Map<String, List<Facet>> parseAscii(List<String> data) {
        Stream<String> stream = data.stream();
        List<String> solidNames = stream
                .filter(s -> !StringUtils.contains(s, "end") && StringUtils.contains(s, "solid "))
                .collect(Collectors.toList());
        long count = solidNames.size();

        if (count < 1) throw new RuntimeException("stl format not valid");

        Map<String, List<Facet>> rtn = new LinkedHashMap<>();
        if (count > 1) {
            solidNames.forEach(n -> {
                int startIndex = data.indexOf(n);
                int endIndex = data.indexOf("end" + n);
                List<String> subData = data.subList(startIndex, endIndex);
                rtn.putAll(parseAsciiUnit(subData));
            });
        } else {
            rtn.putAll(parseAsciiUnit(data));
        }

        return rtn;
    }

    public static List<Facet> parseAsciiWithoutHeader(List<String> data) {
        Stream<String> stream = data.stream();
        List<String> solidNames = stream
                .filter(s -> !StringUtils.contains(s, "end") && StringUtils.contains(s, "solid "))
                .collect(Collectors.toList());
        long count = solidNames.size();

        if (count < 1) throw new RuntimeException("stl format not valid");

        List<Facet> rtn = new ArrayList<>();
        if (count > 1) {
            for (String n : solidNames) {
                int startIndex = data.indexOf(n);
                int endIndex = data.indexOf("end" + n);
                List<String> subData = data.subList(startIndex, endIndex);
                rtn.addAll(parseAsciiUnit(subData).get(n));
            }
        } else {
            for (String n : solidNames) {
                Map<String, List<Facet>> stringListMap = parseAsciiUnit(data);
                List<Facet> facets = stringListMap.get(n.replace("solid ", ""));
                rtn.addAll(facets);
            }
        }

        return rtn;
    }

    private static Map<String, List<Facet>> parseAsciiUnit(List<String> data) {
        Map<String, List<Facet>> rtn = new LinkedHashMap<>();
        data.remove(data.size() - 1);
        String meshName = StringUtils.replace(data.get(0), "solid ", "");
        data.remove(0);
        data.remove(data.size() - 1);
        rtn.put(meshName, parseAsciiAtom(data));
        return rtn;
    }

    private static List<Facet> parseAsciiAtom(List<String> data) {
        final List<Facet> rtnList = new ArrayList<>((int) (data.size() / 7));
        final List<Vertex> pList = new ArrayList<>(3);

        // parse
        data.forEach(s -> {
            if (s.startsWith(" ")){
//                System.out.println("start with space");
                s = StringUtils.stripStart(s, " ");
            } else {
//                System.out.println("start with tab");
                s = s.replace("\t", "");
            }
//            System.out.println(s);
            if (StringUtils.contains(s, "vertex ")) {
                String[] coords = s.replace("vertex ", "").split(" ");
//                for(String s1: coords) {
//                    System.out.println(s1);
//                }
                Vertex e = new Vertex();
                e.getCoord().x = Double.parseDouble(coords[0]);
                e.getCoord().y = Double.parseDouble(coords[1]);
                e.getCoord().z = Double.parseDouble(coords[2]);
                pList.add(e);
            } else if (StringUtils.contains(s, "endloop")) {
                Facet tmp = new Facet(pList.toArray(new Vertex[pList.size()]));
                rtnList.add(tmp);
                pList.clear();
            }
        });
        return rtnList;
    }

    static void updateProgress(double progressPercentage) {
        final int width = 50; // progress bar width in chars

        System.out.print("\r[");
        int i = 0;
        for (; i <= (int) (progressPercentage * width); i++) {
            System.out.print(".");
        }
        for (; i < width; i++) {
            System.out.print(" ");
        }
        System.out.print("]");
    }
}
