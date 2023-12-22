
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterApex implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-apex");
    }
    private native static long tree_sitter_apex();

    private final long ptr;

    public TreeSitterApex() {
        ptr = tree_sitter_apex();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
