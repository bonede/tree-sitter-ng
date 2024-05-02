
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLean extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lean");
    }
    private native static long tree_sitter_lean();

    public TreeSitterLean() {
        super(tree_sitter_lean());
    }

    private TreeSitterLean(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLean(copyPtr());
    }
}
