
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPhp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-php");
    }
    private native static long tree_sitter_php();

    private final long ptr;

    public TreeSitterPhp() {
        ptr = tree_sitter_php();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
