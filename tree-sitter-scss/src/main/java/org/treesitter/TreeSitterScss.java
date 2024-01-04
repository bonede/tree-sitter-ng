
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScss implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scss");
    }
    private native static long tree_sitter_scss();

    private final long ptr;

    public TreeSitterScss() {
        ptr = tree_sitter_scss();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
