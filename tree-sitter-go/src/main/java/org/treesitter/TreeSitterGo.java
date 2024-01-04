
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGo implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go");
    }
    private native static long tree_sitter_go();

    private final long ptr;

    public TreeSitterGo() {
        ptr = tree_sitter_go();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
