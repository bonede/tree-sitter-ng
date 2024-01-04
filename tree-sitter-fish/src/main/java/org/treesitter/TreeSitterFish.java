
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFish implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fish");
    }
    private native static long tree_sitter_fish();

    private final long ptr;

    public TreeSitterFish() {
        ptr = tree_sitter_fish();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
