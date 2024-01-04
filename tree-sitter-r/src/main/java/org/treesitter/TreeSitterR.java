
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterR implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-r");
    }
    private native static long tree_sitter_r();

    private final long ptr;

    public TreeSitterR() {
        ptr = tree_sitter_r();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
