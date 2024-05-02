
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPgn extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pgn");
    }
    private native static long tree_sitter_pgn();

    public TreeSitterPgn() {
        super(tree_sitter_pgn());
    }

    private TreeSitterPgn(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPgn(copyPtr());
    }
}
