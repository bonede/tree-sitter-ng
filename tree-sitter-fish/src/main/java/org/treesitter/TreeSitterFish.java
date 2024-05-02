
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFish extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-fish");
    }
    private native static long tree_sitter_fish();

    public TreeSitterFish() {
        super(tree_sitter_fish());;
    }

    private TreeSitterFish(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterFish(copyPtr());
    }
}
