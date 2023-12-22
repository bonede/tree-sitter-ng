package org.treesitter;

public class TSQueryMatch {
    private int id;
    private int patternIndex;
    private int captureIndex;
    private TSQueryCapture[] captures;

    public int getId() {
        return id;
    }

    public int getPatternIndex() {
        return patternIndex;
    }

    public int getCaptureIndex() {
        return captureIndex;
    }

    public TSQueryCapture[] getCaptures() {
        return captures;
    }
}
