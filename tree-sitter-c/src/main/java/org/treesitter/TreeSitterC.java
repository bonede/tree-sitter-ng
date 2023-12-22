package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterC implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-c");
    }
    private native static long tree_sitter_c();

    private final long ptr;

    public TreeSitterC() {
        ptr = tree_sitter_c();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
