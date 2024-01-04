
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterZig implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-zig");
    }
    private native static long tree_sitter_zig();

    private final long ptr;

    public TreeSitterZig() {
        ptr = tree_sitter_zig();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
