
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterD implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-d");
    }
    private native static long tree_sitter_d();

    private final long ptr;

    public TreeSitterD() {
        ptr = tree_sitter_d();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
