package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSoql implements TSLanguage{
    static {
        NativeUtils.loadLib("lib/tree-sitter-soql");
    }
    private native static long tree_sitter_soql();

    private final long ptr;

    public TreeSitterSoql() {
        ptr = tree_sitter_soql();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
