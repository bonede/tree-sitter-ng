
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCommonlisp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-commonlisp");
    }
    private native static long tree_sitter_commonlisp();

    private final long ptr;

    public TreeSitterCommonlisp() {
        ptr = tree_sitter_commonlisp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
