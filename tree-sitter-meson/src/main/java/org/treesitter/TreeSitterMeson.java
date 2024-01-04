
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMeson implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-meson");
    }
    private native static long tree_sitter_meson();

    private final long ptr;

    public TreeSitterMeson() {
        ptr = tree_sitter_meson();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
