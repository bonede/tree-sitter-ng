
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDot extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dot");
    }
    private native static long tree_sitter_dot();

    public TreeSitterDot() {
        super(tree_sitter_dot());
    }

    private TreeSitterDot(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterDot(copyPtr());
    }
}
