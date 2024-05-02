
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHack extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-hack");
    }
    private native static long tree_sitter_hack();

    public TreeSitterHack() {
        super(tree_sitter_hack());
    }

    private TreeSitterHack(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterHack(copyPtr());
    }
}
