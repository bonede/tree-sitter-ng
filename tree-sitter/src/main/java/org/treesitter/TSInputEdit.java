package org.treesitter;

public class TSInputEdit {
    private final int startByte;
    private final int oldEndByte;
    private final int newEndByte;
    private final TSPoint startPoint;
    private final TSPoint oldEndPoint;
    private final TSPoint newEndPoint;

    public TSInputEdit(int startByte, int oldEndByte, int newEndByte, TSPoint startPoint, TSPoint oldEndPoint, TSPoint newEndPoint) {
        this.startByte = startByte;
        this.oldEndByte = oldEndByte;
        this.newEndByte = newEndByte;
        this.startPoint = startPoint;
        this.oldEndPoint = oldEndPoint;
        this.newEndPoint = newEndPoint;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getOldEndByte() {
        return oldEndByte;
    }

    public int getNewEndByte() {
        return newEndByte;
    }

    public TSPoint getStartPoint() {
        return startPoint;
    }

    public TSPoint getOldEndPoint() {
        return oldEndPoint;
    }

    public TSPoint getNewEndPoint() {
        return newEndPoint;
    }
}
