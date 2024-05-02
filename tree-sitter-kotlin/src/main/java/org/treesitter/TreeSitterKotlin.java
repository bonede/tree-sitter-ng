
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterKotlin extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-kotlin");
    }
    private native static long tree_sitter_kotlin();


    public TreeSitterKotlin() {
        super(tree_sitter_kotlin());
    }

    private TreeSitterKotlin(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterKotlin(getPtr());
    }
}
