
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGoWork implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go-work");
    }
    private native static long tree_sitter_go_work();

    private final long ptr;

    public TreeSitterGoWork() {
        ptr = tree_sitter_go_work();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
