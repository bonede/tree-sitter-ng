package org.treesitter;

/**
 * Interface of parser callback.
 */
public interface TSParserProgress {
    /**
     * Called when parser progressed.
     * @param state The parser state.
     * @return <code>true</code> to stop parsing.
     */
    boolean progress(TSParseState state);
}
