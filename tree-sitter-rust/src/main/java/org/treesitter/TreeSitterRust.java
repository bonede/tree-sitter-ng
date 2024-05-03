
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRust extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rust");
    }
    private native static long tree_sitter_rust();

    public TreeSitterRust() {
        super(tree_sitter_rust());
    }

    private TreeSitterRust(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRust(copyPtr());
    }
}
