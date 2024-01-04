
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScala implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scala");
    }
    private native static long tree_sitter_scala();

    private final long ptr;

    public TreeSitterScala() {
        ptr = tree_sitter_scala();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
