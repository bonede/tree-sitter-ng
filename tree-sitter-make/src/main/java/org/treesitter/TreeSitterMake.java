
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMake implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-make");
    }
    private native static long tree_sitter_make();

    private final long ptr;

    public TreeSitterMake() {
        ptr = tree_sitter_make();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
