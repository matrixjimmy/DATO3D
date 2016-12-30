package com.anhe3d.domain;

import com.anhe3d.domain.javax.vecmath.Point3d;

import java.util.Arrays;

/**
 * Created by kevinhung on 5/15/16.
 */
public class Edge {
    private Vertex[] vertexes;

    public Edge(Vertex[] vertices) {
        this.vertexes = new Vertex[2];
        this.vertexes = vertices;
    }

    public Edge(Vertex v1, Vertex v2) {
        this.vertexes = new Vertex[2];
        this.vertexes[0] = v1;
        this.vertexes[1] = v2;
    }

    protected Edge() {
        this.vertexes = new Vertex[2];
    }

    public Vertex[] getVertexes() {
        return vertexes;
    }

    public void setVertexes(Vertex[] vertexes) {
        this.vertexes = vertexes;
    }

    public double getZMax() {
        if(vertexes[0].getCoord().getZ() >= vertexes[1].getCoord().getZ()) {
            return vertexes[0].getCoord().getZ();
        } else {
            return vertexes[1].getCoord().getZ();
        }
    }

    public double getZMin() {
        if(vertexes[0].getCoord().getZ() <= vertexes[1].getCoord().getZ()) {
            return vertexes[0].getCoord().getZ();
        } else {
            return vertexes[1].getCoord().getZ();
        }
    }

    public double getXMax() {
        if(vertexes[0].getCoord().getX() >= vertexes[1].getCoord().getX()) {
            return vertexes[0].getCoord().getX();
        } else {
            return vertexes[1].getCoord().getX();
        }
    }

    public double getXMin() {
        if(vertexes[0].getCoord().getX() <= vertexes[1].getCoord().getX()) {
            return vertexes[0].getCoord().getX();
        } else {
            return vertexes[1].getCoord().getX();
        }
    }

    public double getYMax() {
        if(vertexes[0].getCoord().getY() >= vertexes[1].getCoord().getY()) {
            return vertexes[0].getCoord().getY();
        } else {
            return vertexes[1].getCoord().getY();
        }
    }

    public double getYMin() {
        if(vertexes[0].getCoord().getY() <= vertexes[1].getCoord().getY()) {
            return vertexes[0].getCoord().getY();
        } else {
            return vertexes[1].getCoord().getY();
        }
    }

    public Vertex getHigherVertex() {
        if(vertexes[0].getCoord().getZ() >= vertexes[1].getCoord().getZ()) {
            return vertexes[0];
        } else {
            return vertexes[1];
        }
    }

    public Point3d getPoint3dByHeight(double height) {
        Point3d point1 = vertexes[0].getCoord();
        Point3d point2 = vertexes[1].getCoord();
        double portion = (point1.z-height) / (height-point2.z);

        double x = (point1.x + portion*point2.x) / (portion+1);
        double y = (point1.y + portion*point2.y) / (portion+1);
        return new Point3d(x, y, height);
    }

    public boolean isEqual(Edge edge) {
        return (vertexes[0].getCoord().equals(edge.vertexes[0].getCoord()) && vertexes[1].getCoord().equals(edge.vertexes[1].getCoord()))
                || (vertexes[0].getCoord().equals(edge.vertexes[1].getCoord()) && vertexes[1].getCoord().equals(edge.vertexes[0].getCoord()));
    }

    public String toOnlyVertexes() {
        return "vertexes=" + Arrays.toString(vertexes);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertexes=" + Arrays.toString(vertexes) +
                '}';
    }
}
