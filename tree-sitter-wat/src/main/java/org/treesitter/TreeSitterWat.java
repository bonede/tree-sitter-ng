
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWat implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wat");
    }
    private native static long tree_sitter_wat();

    private final long ptr;

    public TreeSitterWat() {
        ptr = tree_sitter_wat();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
