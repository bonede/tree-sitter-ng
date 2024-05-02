package org.treesitter;

import org.treesitter.TSLanguage;
import org.treesitter.utils.NativeUtils;

public class TreeSitterJson extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-json");
    }
    private native static long tree_sitter_json();

    public TreeSitterJson() {
        super(tree_sitter_json());
    }

    protected TreeSitterJson(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterJson(copyPtr());
    }
}
