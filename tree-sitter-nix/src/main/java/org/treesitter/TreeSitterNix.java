
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNix extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nix");
    }
    private native static long tree_sitter_nix();

    public TreeSitterNix() {
        super(tree_sitter_nix());
    }

    private TreeSitterNix(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterNix(copyPtr());
    }
}
