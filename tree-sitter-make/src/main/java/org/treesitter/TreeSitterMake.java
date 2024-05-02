
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMake extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-make");
    }
    private native static long tree_sitter_make();

    public TreeSitterMake() {
        super(tree_sitter_make());
    }

    private TreeSitterMake(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterMake(copyPtr());
    }
}
