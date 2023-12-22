
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterBash implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-bash");
    }
    private native static long tree_sitter_bash();

    private final long ptr;

    public TreeSitterBash() {
        ptr = tree_sitter_bash();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
