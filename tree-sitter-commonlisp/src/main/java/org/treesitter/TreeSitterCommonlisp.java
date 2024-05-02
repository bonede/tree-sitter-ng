
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCommonlisp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-commonlisp");
    }
    private native static long tree_sitter_commonlisp();

    public TreeSitterCommonlisp() {
        super(tree_sitter_commonlisp());
    }

    private TreeSitterCommonlisp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCommonlisp(copyPtr());
    }
}
