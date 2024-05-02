
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCSharp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-c-sharp");
    }

    private native static long tree_sitter_c_sharp();

    public TreeSitterCSharp() {
        super(tree_sitter_c_sharp());
    }

    protected TreeSitterCSharp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCSharp(copyPtr());
    }
}
