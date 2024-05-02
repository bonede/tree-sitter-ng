
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLlvmMir extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-llvm-mir");
    }
    private native static long tree_sitter_llvm_mir();

    public TreeSitterLlvmMir() {
        super(tree_sitter_llvm_mir());
    }

    public TreeSitterLlvmMir(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLlvmMir(copyPtr());
    }
}
