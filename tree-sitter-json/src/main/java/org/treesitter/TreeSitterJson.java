package org.treesitter;

import org.treesitter.TSLanguage;
import org.treesitter.utils.NativeUtils;

public class TreeSitterJson implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-json");
    }
    private native static long tree_sitter_json();

    private long ptr;

    public TreeSitterJson() {
        ptr = tree_sitter_json();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
