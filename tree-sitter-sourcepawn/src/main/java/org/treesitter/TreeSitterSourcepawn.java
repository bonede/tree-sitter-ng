
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSourcepawn implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sourcepawn");
    }
    private native static long tree_sitter_sourcepawn();

    private final long ptr;

    public TreeSitterSourcepawn() {
        ptr = tree_sitter_sourcepawn();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
