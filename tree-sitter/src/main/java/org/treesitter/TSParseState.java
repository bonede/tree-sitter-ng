package org.treesitter;

public class TSParseState {
    private int currentByteOffset;
    private boolean isError;

    public boolean hasError() {
        return isError;
    }

    public int getCurrentByteOffset() {
        return currentByteOffset;
    }


}
