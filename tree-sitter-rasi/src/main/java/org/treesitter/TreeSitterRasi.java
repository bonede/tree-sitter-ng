
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRasi implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rasi");
    }
    private native static long tree_sitter_rasi();

    private final long ptr;

    public TreeSitterRasi() {
        ptr = tree_sitter_rasi();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
