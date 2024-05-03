
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSexp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sexp");
    }
    private native static long tree_sitter_sexp();

    public TreeSitterSexp() {
        super(tree_sitter_sexp());
    }

    private TreeSitterSexp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSexp(copyPtr());
    }


}
