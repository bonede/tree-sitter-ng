
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPgn implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pgn");
    }
    private native static long tree_sitter_pgn();

    private final long ptr;

    public TreeSitterPgn() {
        ptr = tree_sitter_pgn();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
