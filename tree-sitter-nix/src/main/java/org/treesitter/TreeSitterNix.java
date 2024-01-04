
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNix implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nix");
    }
    private native static long tree_sitter_nix();

    private final long ptr;

    public TreeSitterNix() {
        ptr = tree_sitter_nix();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
