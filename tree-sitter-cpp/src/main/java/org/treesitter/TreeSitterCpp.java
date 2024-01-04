
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCpp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cpp");
    }
    private native static long tree_sitter_cpp();

    private final long ptr;

    public TreeSitterCpp() {
        ptr = tree_sitter_cpp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
