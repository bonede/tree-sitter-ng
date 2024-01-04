
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLalrpop implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lalrpop");
    }
    private native static long tree_sitter_lalrpop();

    private final long ptr;

    public TreeSitterLalrpop() {
        ptr = tree_sitter_lalrpop();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
