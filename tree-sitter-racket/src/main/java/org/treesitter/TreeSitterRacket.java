
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRacket extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-racket");
    }
    private native static long tree_sitter_racket();

    public TreeSitterRacket() {
        super(tree_sitter_racket());
    }

    private TreeSitterRacket(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRacket(copyPtr());
    }
}
