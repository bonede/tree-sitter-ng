
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTablegen implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-tablegen");
    }
    private native static long tree_sitter_tablegen();

    private final long ptr;

    public TreeSitterTablegen() {
        ptr = tree_sitter_tablegen();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
