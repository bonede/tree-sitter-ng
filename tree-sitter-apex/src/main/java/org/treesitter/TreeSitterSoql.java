package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSoql extends TSLanguage{
    static {
        NativeUtils.loadLib("lib/tree-sitter-soql");
    }
    private native static long tree_sitter_soql();

    public TreeSitterSoql() {
        super(tree_sitter_soql());
    }

    protected TreeSitterSoql(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSoql(copyPtr());
    }
}
