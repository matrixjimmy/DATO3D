package com.anhe3d.domain;

/**
 * Created by kevinhung on 5/13/16.
 */
public class EdgePair {
    private Edge forwardEdge;
    private Edge backwardEdge;

    public EdgePair(Edge forwardEdge, Edge backwardEdge) {
        this.forwardEdge = forwardEdge;
        this.backwardEdge = backwardEdge;
    }




    public Edge getForwardEdge() {
        return forwardEdge;
    }

    public void setForwardEdge(Edge forwardEdge) {
        this.forwardEdge = forwardEdge;
    }

    public Edge getBackwardEdge() {
        return backwardEdge;
    }

    public void setBackwardEdge(Edge backwardEdge) {
        this.backwardEdge = backwardEdge;
    }

    @Override
    public String toString() {
        return "EdgePair{" +
                "forwardEdge=" + forwardEdge +
                ", backwardEdge=" + backwardEdge +
                '}';
    }
}
