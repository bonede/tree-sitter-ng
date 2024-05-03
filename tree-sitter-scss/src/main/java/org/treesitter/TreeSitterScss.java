
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScss extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scss");
    }
    private native static long tree_sitter_scss();

    public TreeSitterScss() {
        super(tree_sitter_scss());
    }

    private TreeSitterScss(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterScss(copyPtr());
    }
}
