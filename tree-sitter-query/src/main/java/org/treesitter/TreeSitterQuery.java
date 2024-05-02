
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterQuery extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-query");
    }
    private native static long tree_sitter_query();

    public TreeSitterQuery() {
        super(tree_sitter_query());
    }

    private TreeSitterQuery(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterQuery(copyPtr());
    }
}
