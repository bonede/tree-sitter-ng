
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElisp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elisp");
    }
    private native static long tree_sitter_elisp();


    public TreeSitterElisp() {
        super(tree_sitter_elisp());
    }

    private TreeSitterElisp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterElisp(copyPtr());
    }
}
