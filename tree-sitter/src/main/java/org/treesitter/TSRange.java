package org.treesitter;

public class TSRange {
    private TSPoint startPoint;
    private TSPoint endPoint;
    private int startByte;
    private int endByte;

    public TSRange(TSPoint startPoint, TSPoint endPoint, int startByte, int endByte) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public TSPoint getStartPoint() {
        return startPoint;
    }

    public TSPoint getEndPoint() {
        return endPoint;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }
}
