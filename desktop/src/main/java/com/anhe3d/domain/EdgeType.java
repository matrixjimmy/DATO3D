package com.anhe3d.domain;

/**
 * Created by jimmy on 2015/11/24.
 */
public enum EdgeType {

    //S1: Edge with Vzmax and Vzmin (Vzmax = maximum height of Vertex)
    //S2: Edge with Vzmin and Vzmed
    //S3: Edge with Vzmed and Vzmax, where Vzmax >= Vzmed >= Vzmin
    //SF: Vzmax = Vzmed = Vzmin redundant in the algorithm of Zhang 2015

    S1,
    S2,
    S3,
    SF;


//    A1(new int[][]{{1, 2}, {0, 1}, {2, 0}}),
//    A2(new int[][]{{0, 1}, {2, 0}, {1, 2}}),
//    B(new int[][]{{2, 0}, {1, 2}, {0, 1}}),
//    C1(new int[][]{{0, 1}, {1, 2}, {2, 0}}),
//    C2(new int[][]{{1, 2}, {2, 0}, {0, 1}}),
//    D(new int[][]{{2, 0}, {0, 1}, {1, 2}}),
//    E(new int[][]{{-1, -1}, {-1, -1}, {-1, -1}});
//    private int[][] value = new int[3][2];
//
//    EdgeType(int[][] value) {
//        this.value = value;
//    }
//
//    public int[][] getValue() {
//        return value;
//    }
//
//    public int getMaxZ() {
//        return value[0][1];
//    }
//
//    public int getMinZ() {
//        return value[0][0];
//    }
//
//    public int getMidZ() {
//        int maxZ = value[0][1];
//        int minZ = value[0][0];
//        if (-1 == maxZ) {
//            return -1;
//        }
//        if (2 == maxZ) {
//            if (1 == minZ) {
//                return 0;
//            }
//            if (0 == minZ) {
//                return 1;
//            }
//        }
//        if (1 == maxZ) {
//            if (2 == minZ) {
//                return 0;
//            }
//            if (0 == minZ) {
//                return 2;
//            }
//        }
//        if (0 == maxZ) {
//            if (1 == minZ) {
//                return 2;
//            }
//            if (2 == minZ) {
//                return 1;
//            }
//        }
//        throw new RuntimeException("containing wrong value!");ㄡ
//    }
}
