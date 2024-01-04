
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLlvmMir implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-llvm-mir");
    }
    private native static long tree_sitter_llvm_mir();

    private final long ptr;

    public TreeSitterLlvmMir() {
        ptr = tree_sitter_llvm_mir();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
