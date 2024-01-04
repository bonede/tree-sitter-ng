
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRacket implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-racket");
    }
    private native static long tree_sitter_racket();

    private final long ptr;

    public TreeSitterRacket() {
        ptr = tree_sitter_racket();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
