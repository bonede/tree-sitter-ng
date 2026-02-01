
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCpp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cpp");
    }
    private native static long tree_sitter_cpp();

    public TreeSitterCpp() {
        super(tree_sitter_cpp());
    }

    private TreeSitterCpp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCpp(copyPtr());
    }
}
