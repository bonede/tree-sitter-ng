
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHack implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-hack");
    }
    private native static long tree_sitter_hack();

    private final long ptr;

    public TreeSitterHack() {
        ptr = tree_sitter_hack();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
