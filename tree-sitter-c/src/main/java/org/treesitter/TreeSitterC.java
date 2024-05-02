package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterC extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-c");
    }
    private native static long tree_sitter_c();

    public TreeSitterC() {
        super(tree_sitter_c());
    }

    protected TreeSitterC(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterC(copyPtr());
    }
}
