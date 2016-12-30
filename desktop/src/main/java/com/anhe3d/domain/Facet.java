package com.anhe3d.domain;

import com.anhe3d.domain.javax.vecmath.Color4b;
import com.anhe3d.domain.javax.vecmath.Point3d;
import com.anhe3d.domain.javax.vecmath.Vector3d;

/**
 * Created by jimmy on 2015/10/12.
 *
 * Edges will determine by the constructor with the method of determineEdges
 */
public class Facet {
    private static final double EPSILON = 1E-6;
    private Vector3d normal;
    //Array vertexes contains the flag order by the index of array
    private Vertex[] vertexes;
    private Edge[] edges;
//    private EdgeType sideType;


    protected Facet() {
        vertexes = new Vertex[3];
        vertexes[0] = new Vertex();
        vertexes[1] = new Vertex();
        vertexes[2] = new Vertex();
        normal = new Vector3d();
        edges = new Edge[3];
    }

    public Facet(Vertex[] vertexes) {
        this.vertexes = new Vertex[3];
        this.vertexes = vertexes;
        this.normal = calculateNormal();
        this.edges = determineEdges();
    }

    public Facet(Point3d point1, Point3d point2, Point3d point3) {
        vertexes = new Vertex[3];
        this.vertexes[0] = new Vertex();
        this.vertexes[1] = new Vertex();
        this.vertexes[2] = new Vertex();

        this.vertexes[0].setCoord(point1);
        this.vertexes[1].setCoord(point2);
        this.vertexes[2].setCoord(point3);

        this.vertexes[0].setColor(new Color4b());
        this.vertexes[1].setColor(new Color4b());
        this.vertexes[2].setColor(new Color4b());

        this.normal = calculateNormal();
        this.edges = determineEdges();
    }

    public Vector3d getNormal() {
        return normal;
    }

    public void setNormal(Vector3d normal) {
        this.normal = normal;
    }

    public Vertex[] getVertexes() {
        return vertexes;
    }

    public void setVertexes(Vertex[] vertexes) {
        this.vertexes = vertexes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }

    //    public EdgeType getSideType() {
//        return sideType;
//    }
//
//    public void setSideType(EdgeType sideType) {
//        this.sideType = sideType;
//    }

    private Vector3d calculateNormal() {
        Vector3d v1 = new Vector3d(vertexes[0].getCoord().x - vertexes[2].getCoord().x, vertexes[0].getCoord().y - vertexes[2].getCoord().y, vertexes[0].getCoord().z - vertexes[2].getCoord().z);
        Vector3d v2 = new Vector3d(vertexes[1].getCoord().x - vertexes[2].getCoord().x, vertexes[1].getCoord().y - vertexes[2].getCoord().y, vertexes[1].getCoord().z - vertexes[2].getCoord().z);
        normal = new Vector3d();
        normal.cross(v1, v2);
        normal.normalize();
        return normal;
    }

    private Edge[] determineEdges() {
        Edge[] edges= new Edge[3];
        double p0z = vertexes[0].getCoord().z;
        double p1z = vertexes[1].getCoord().z;
        double p2z = vertexes[2].getCoord().z;

        double diff01 = (p0z - p1z > 0 ? p0z - p1z : -(p0z - p1z));
        boolean a = diff01 > EPSILON;
        double diff12 = (p1z - p2z > 0 ? p1z - p2z : -(p1z - p2z));
        boolean b = diff12 > EPSILON;
        double diff20 = (p2z - p0z > 0 ? p2z - p0z : -(p2z - p0z));
        boolean c = diff20 > EPSILON;


//        Edge edge1 = new Edge(vertexes[0], vertexes[1]);
//        Edge edge2 = new Edge(vertexes[1], vertexes[2]);
//        Edge edge3 = new Edge(vertexes[2], vertexes[0]);

        if (a || b || c) {
            if (diff01 >= diff12 && diff01 >= diff20) {
                edges[0] = new Edge(vertexes[0], vertexes[1]);
//                System.out.println(edges[0].getVertexes()[0].toString() +" "+ edges[0].getVertexes()[1].toString());
                if (p0z > p1z) {
                    edges[1] = new Edge(vertexes[1], vertexes[2]);
                    edges[2] = new Edge(vertexes[2], vertexes[0]);
                } else {
                    edges[1] = new Edge(vertexes[2], vertexes[0]);
                    edges[2] = new Edge(vertexes[1], vertexes[2]);
                }
            } else if (diff12 >= diff01 && diff12 >= diff20) {
                edges[0] = new Edge(vertexes[1], vertexes[2]);
//                System.out.println(edges[0].getVertexes()[0].toString() +" "+ edges[0].getVertexes()[1].toString());
                if (p1z > p2z) {
                    edges[1] = new Edge(vertexes[2], vertexes[0]);
                    edges[2] = new Edge(vertexes[0], vertexes[1]);
                } else {
                    edges[1] = new Edge(vertexes[0], vertexes[1]);
                    edges[2] = new Edge(vertexes[2], vertexes[0]);
                }
            } else if (diff20 >= diff01 && diff20 >= diff12) {
                edges[0] = new Edge(vertexes[2], vertexes[0]);
//                System.out.println(edges[0].getVertexes()[0].toString() +" "+ edges[0].getVertexes()[1].toString());
                if (p2z > p0z) {
                    edges[1] = new Edge(vertexes[0], vertexes[1]);
                    edges[2] = new Edge(vertexes[1], vertexes[2]);
                } else {
                    edges[1] = new Edge(vertexes[1], vertexes[2]);
                    edges[2] = new Edge(vertexes[0], vertexes[1]);
                }
            }
            return edges;
        } else {
            edges[0] = new Edge(vertexes[0], vertexes[1]);
            edges[1] = new Edge(vertexes[1], vertexes[2]);
            edges[2] = new Edge(vertexes[2], vertexes[0]);
            return edges;
        }
//        if (a || b || c) {
//            if (p2z >= p0z && p0z >= p1z) {
//                sideType = EdgeType.A1;
//            }
//            if (p1z >= p2z && p2z >= p0z) {
//                sideType = EdgeType.A2;
//            }
//            if (p0z >= p1z && p1z >= p2z) {
//                sideType = EdgeType.B;
//            }
//            if (p0z >= p2z && p2z >= p1z) {
//                sideType = EdgeType.C1;
//            }
//            if (p1z >= p0z && p0z >= p2z) {
//                sideType = EdgeType.C2;
//            }
//            if (p2z >= p1z && p1z >= p0z) {
//                sideType = EdgeType.D;
//            }
//        } else {
//            sideType = EdgeType.E;
//        }

    }




//    private EdgeType determineSideType() {
//        double p0z = vertexes[0].getCoord().z;
//        double p1z = vertexes[1].getCoord().z;
//        double p2z = vertexes[2].getCoord().z;
//
//        double diff = p0z - p1z;
//        boolean a = (diff > 0 ? diff : -diff) > EPSILON;
//        diff = p1z - p2z;
//        boolean b = (diff > 0 ? diff : -diff) > EPSILON;
//        diff = p0z - p2z;
//        boolean c = (diff > 0 ? diff : -diff) > EPSILON;
//
//        if (a || b || c) {
//            if (p2z >= p0z && p0z >= p1z) {
//                sideType = EdgeType.A1;
//            }
//            if (p1z >= p2z && p2z >= p0z) {
//                sideType = EdgeType.A2;
//            }
//            if (p0z >= p1z && p1z >= p2z) {
//                sideType = EdgeType.B;
//            }
//            if (p0z >= p2z && p2z >= p1z) {
//                sideType = EdgeType.C1;
//            }
//            if (p1z >= p0z && p0z >= p2z) {
//                sideType = EdgeType.C2;
//            }
//            if (p2z >= p1z && p1z >= p0z) {
//                sideType = EdgeType.D;
//            }
//        } else {
//            sideType = EdgeType.E;
//        }
//        return sideType;
//    }

}
