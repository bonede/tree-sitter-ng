
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterEno implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-eno");
    }
    private native static long tree_sitter_eno();

    private final long ptr;

    public TreeSitterEno() {
        ptr = tree_sitter_eno();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
