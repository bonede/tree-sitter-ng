
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRst implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rst");
    }
    private native static long tree_sitter_rst();

    private final long ptr;

    public TreeSitterRst() {
        ptr = tree_sitter_rst();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
