
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWast extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wast");
    }
    private native static long tree_sitter_wast();

    public TreeSitterWast() {
        super(tree_sitter_wast());
    }

    private TreeSitterWast(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterWast(copyPtr());
    }
}
