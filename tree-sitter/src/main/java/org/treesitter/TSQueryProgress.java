package org.treesitter;

/**
 * Interface to query progress.
 */
public interface TSQueryProgress {
    /**
     * Called when cursor progressed.
     * @param state The cursor state.
     * @return return <code>false</code> to continue.
     */
    boolean progress(TSQueryCursorState state);
}
