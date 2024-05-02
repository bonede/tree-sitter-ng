
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFortran extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fortran");
    }
    private native static long tree_sitter_fortran();

    public TreeSitterFortran() {
        super(tree_sitter_fortran());
    }

    private TreeSitterFortran(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterFortran(copyPtr());
    }
}
