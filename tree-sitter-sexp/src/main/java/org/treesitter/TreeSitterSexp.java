
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSexp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sexp");
    }
    private native static long tree_sitter_sexp();

    private final long ptr;

    public TreeSitterSexp() {
        ptr = tree_sitter_sexp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
