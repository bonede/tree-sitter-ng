
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterAgda implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-agda");
    }
    private native static long tree_sitter_agda();

    private final long ptr;

    public TreeSitterAgda() {
        ptr = tree_sitter_agda();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
