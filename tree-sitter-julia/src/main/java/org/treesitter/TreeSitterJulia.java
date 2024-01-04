
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJulia implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-julia");
    }
    private native static long tree_sitter_julia();

    private final long ptr;

    public TreeSitterJulia() {
        ptr = tree_sitter_julia();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
