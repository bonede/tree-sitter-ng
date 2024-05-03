
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRst extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rst");
    }
    private native static long tree_sitter_rst();


    public TreeSitterRst() {
        super(tree_sitter_rst());
    }

    private TreeSitterRst(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRst(copyPtr());
    }
}
