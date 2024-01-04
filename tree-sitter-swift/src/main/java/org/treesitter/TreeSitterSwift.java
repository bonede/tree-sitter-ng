
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSwift implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-swift");
    }
    private native static long tree_sitter_swift();

    private final long ptr;

    public TreeSitterSwift() {
        ptr = tree_sitter_swift();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
