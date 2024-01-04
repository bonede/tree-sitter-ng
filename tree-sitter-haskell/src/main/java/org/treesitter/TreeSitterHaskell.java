
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHaskell implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-haskell");
    }
    private native static long tree_sitter_haskell();

    private final long ptr;

    public TreeSitterHaskell() {
        ptr = tree_sitter_haskell();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
