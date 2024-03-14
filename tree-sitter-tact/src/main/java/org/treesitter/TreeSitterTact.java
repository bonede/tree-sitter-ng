
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTact implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-tact");
    }
    private native static long tree_sitter_tact();

    private final long ptr;

    public TreeSitterTact() {
        ptr = tree_sitter_tact();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
