
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterR extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-r");
    }
    private native static long tree_sitter_r();

    public TreeSitterR() {
        super(tree_sitter_r());
    }

    private TreeSitterR(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterR(copyPtr());
    }
}
