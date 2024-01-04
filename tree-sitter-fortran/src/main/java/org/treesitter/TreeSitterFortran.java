
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFortran implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fortran");
    }
    private native static long tree_sitter_fortran();

    private final long ptr;

    public TreeSitterFortran() {
        ptr = tree_sitter_fortran();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
