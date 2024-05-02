
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFennel extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fennel");
    }
    private native static long tree_sitter_fennel();

    public TreeSitterFennel() {
        super(tree_sitter_fennel());
    }

    private TreeSitterFennel(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterFennel(copyPtr());
    }
}
