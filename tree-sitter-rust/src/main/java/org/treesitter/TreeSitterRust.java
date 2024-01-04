
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRust implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rust");
    }
    private native static long tree_sitter_rust();

    private final long ptr;

    public TreeSitterRust() {
        ptr = tree_sitter_rust();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
