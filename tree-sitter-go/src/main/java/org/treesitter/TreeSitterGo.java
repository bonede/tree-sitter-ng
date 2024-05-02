
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGo extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go");
    }
    private native static long tree_sitter_go();

    public TreeSitterGo() {
        super(tree_sitter_go());
    }

    private TreeSitterGo(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGo(copyPtr());
    }
}
