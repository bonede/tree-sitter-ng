
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDot implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dot");
    }
    private native static long tree_sitter_dot();

    private final long ptr;

    public TreeSitterDot() {
        ptr = tree_sitter_dot();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
