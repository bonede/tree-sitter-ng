
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCmake implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cmake");
    }
    private native static long tree_sitter_cmake();

    private final long ptr;

    public TreeSitterCmake() {
        ptr = tree_sitter_cmake();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
