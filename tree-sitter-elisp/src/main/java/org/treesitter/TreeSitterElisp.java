
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElisp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elisp");
    }
    private native static long tree_sitter_elisp();

    private final long ptr;

    public TreeSitterElisp() {
        ptr = tree_sitter_elisp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
