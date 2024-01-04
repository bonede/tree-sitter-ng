
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLlvm implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-llvm");
    }
    private native static long tree_sitter_llvm();

    private final long ptr;

    public TreeSitterLlvm() {
        ptr = tree_sitter_llvm();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
