
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElm implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elm");
    }
    private native static long tree_sitter_elm();

    private final long ptr;

    public TreeSitterElm() {
        ptr = tree_sitter_elm();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
