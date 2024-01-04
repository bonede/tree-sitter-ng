
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGleam implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gleam");
    }
    private native static long tree_sitter_gleam();

    private final long ptr;

    public TreeSitterGleam() {
        ptr = tree_sitter_gleam();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
