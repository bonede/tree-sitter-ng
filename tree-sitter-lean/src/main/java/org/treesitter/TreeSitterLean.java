
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLean implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lean");
    }
    private native static long tree_sitter_lean();

    private final long ptr;

    public TreeSitterLean() {
        ptr = tree_sitter_lean();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
