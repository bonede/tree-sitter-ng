
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScheme implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scheme");
    }
    private native static long tree_sitter_scheme();

    private final long ptr;

    public TreeSitterScheme() {
        ptr = tree_sitter_scheme();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
