
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSourcepawn extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sourcepawn");
    }
    private native static long tree_sitter_sourcepawn();

    public TreeSitterSourcepawn() {
        super(tree_sitter_sourcepawn());
    }

    private TreeSitterSourcepawn(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSourcepawn(copyPtr());
    }
}
