
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterZig extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-zig");
    }
    private native static long tree_sitter_zig();

    public TreeSitterZig() {
        super(tree_sitter_zig());
    }

    private TreeSitterZig(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterZig(copyPtr());
    }


}
