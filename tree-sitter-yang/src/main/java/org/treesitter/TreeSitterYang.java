
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterYang implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-yang");
    }
    private native static long tree_sitter_yang();

    private final long ptr;

    public TreeSitterYang() {
        ptr = tree_sitter_yang();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
