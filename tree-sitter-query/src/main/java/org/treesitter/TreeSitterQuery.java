
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterQuery implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-query");
    }
    private native static long tree_sitter_query();

    private final long ptr;

    public TreeSitterQuery() {
        ptr = tree_sitter_query();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
