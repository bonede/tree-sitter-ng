
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterComment implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-comment");
    }
    private native static long tree_sitter_comment();

    private final long ptr;

    public TreeSitterComment() {
        ptr = tree_sitter_comment();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
