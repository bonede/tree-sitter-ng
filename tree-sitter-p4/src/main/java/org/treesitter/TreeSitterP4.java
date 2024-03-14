
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterP4 implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-p4");
    }
    private native static long tree_sitter_p4();

    private final long ptr;

    public TreeSitterP4() {
        ptr = tree_sitter_p4();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
