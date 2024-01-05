package org.treesitter;

/**
 * Interface for custom source code reader
 */
public interface TSReader {
    /**
     * The read method is called when the parser needs to read more data from the source code.
     * @param buf buffer to write data to
     * @param offset offset to read from
     * @param position position to read from
     * @return <code>0</code> if the end of the source code was reached, otherwise the number of bytes read.
     */
    int read(byte[] buf, int offset, TSPoint position);
}
