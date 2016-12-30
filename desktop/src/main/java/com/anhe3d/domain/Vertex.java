package com.anhe3d.domain;

import com.anhe3d.domain.javax.vecmath.Color4b;
import com.anhe3d.domain.javax.vecmath.Point3d;

/**
 * Created by jimmy on 2015/11/19.
 */
public class Vertex {
    private static final double EPSILON = 1E-6;
    private Point3d coord = new Point3d();
    private Color4b color = new Color4b();

    public Point3d getCoord() {
        return coord;
    }

    public void setCoord(Point3d coord) {
        this.coord = coord;
    }

    public Color4b getColor() {
        return color;
    }

    public void setColor(Color4b color) {
        this.color = color;
    }

    public boolean isEqualZ(Vertex v2) {
        double diff = (coord.z - v2.getCoord().z > 0 ? coord.z - v2.getCoord().z : -(coord.z - v2.getCoord().z));
        if (diff > EPSILON) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "coord=" + coord +
                ", color=" + color +
                '}';
    }
}
