
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWast implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wast");
    }
    private native static long tree_sitter_wast();

    private final long ptr;

    public TreeSitterWast() {
        ptr = tree_sitter_wast();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
