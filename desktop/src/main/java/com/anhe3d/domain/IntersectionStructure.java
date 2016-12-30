package com.anhe3d.domain;

/**
 * Created by kevinhung on 5/16/16.
 */
public class IntersectionStructure {

    private Vertex interVertex;
    private EdgePair edgePair;

    public IntersectionStructure(Vertex interVertex, EdgePair edgePair) {
        this.interVertex = interVertex;
        this.edgePair = edgePair;
    }

    public Vertex getInterVertex() {
        return interVertex;
    }

    public void setInterVertex(Vertex interVertex) {
        this.interVertex = interVertex;
    }

    public EdgePair getEdgePair() {
        return edgePair;
    }

    public void setEdgePair(EdgePair edgePair) {
        this.edgePair = edgePair;
    }

    public String toInterVertexString() {
        return "IntersectionStructure{" +
                "interVertex=" + interVertex +
                '}';
    }

    public String toEdgePair() {
        return "forward: " + edgePair.getForwardEdge().toOnlyVertexes();
    }

    @Override
    public String toString() {
        return "IntersectionStructure{" +
                "interVertex=" + interVertex +
                ", edgePair=" + edgePair +
                '}';
    }



}
