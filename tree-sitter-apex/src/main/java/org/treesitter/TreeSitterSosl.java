package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSosl extends TSLanguage{
    static {
        NativeUtils.loadLib("lib/tree-sitter-sosl");
    }
    private native static long tree_sitter_sosl();

    public TreeSitterSosl() {
        super(tree_sitter_sosl());
    }

    protected TreeSitterSosl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSosl(copyPtr());
    }
}
