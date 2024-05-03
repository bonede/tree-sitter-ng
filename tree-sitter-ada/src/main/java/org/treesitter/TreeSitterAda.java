package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterAda extends TSLanguage{
    static {
        NativeUtils.loadLib("lib/tree-sitter-ada");
    }
    private native static long tree_sitter_ada();

    public TreeSitterAda() {
        super(tree_sitter_ada());
    }

    private TreeSitterAda(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterAda(copyPtr());
    }
}