
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFennel implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fennel");
    }
    private native static long tree_sitter_fennel();

    private final long ptr;

    public TreeSitterFennel() {
        ptr = tree_sitter_fennel();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
