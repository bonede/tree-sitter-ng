
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterP4 extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-p4");
    }
    private native static long tree_sitter_p4();

    public TreeSitterP4() {
        super(tree_sitter_p4());
    }

    private TreeSitterP4(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterP4(copyPtr());
    }
}
