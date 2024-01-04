
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDart implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dart");
    }
    private native static long tree_sitter_dart();

    private final long ptr;

    public TreeSitterDart() {
        ptr = tree_sitter_dart();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
