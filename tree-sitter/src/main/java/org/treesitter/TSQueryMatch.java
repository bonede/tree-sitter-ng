package org.treesitter;

import java.util.HashMap;
import java.util.Map;

public class TSQueryMatch {
    private int id;
    private int patternIndex;
    private int captureIndex;
    private TSQueryCapture[] captures;
    private Map<String, String> metadata = new HashMap<>();

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

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
