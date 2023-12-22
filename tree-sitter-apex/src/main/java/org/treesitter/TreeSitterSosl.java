package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSosl implements TSLanguage{
    static {
        NativeUtils.loadLib("lib/tree-sitter-sosl");
    }
    private native static long tree_sitter_sosl();

    private final long ptr;

    public TreeSitterSosl() {
        ptr = tree_sitter_sosl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
