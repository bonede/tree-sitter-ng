
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPascal implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pascal");
    }
    private native static long tree_sitter_pascal();

    private final long ptr;

    public TreeSitterPascal() {
        ptr = tree_sitter_pascal();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
