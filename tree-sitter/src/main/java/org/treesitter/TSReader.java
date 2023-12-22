package org.treesitter;

public interface TSReader {
    int read(byte[] buf, int offset, TSPoint position);
}
