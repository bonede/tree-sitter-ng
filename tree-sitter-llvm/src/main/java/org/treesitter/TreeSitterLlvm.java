
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLlvm extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-llvm");
    }
    private native static long tree_sitter_llvm();

    public TreeSitterLlvm() {
        super(tree_sitter_llvm());
    }

    private TreeSitterLlvm(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLlvm(copyPtr());
    }
}
