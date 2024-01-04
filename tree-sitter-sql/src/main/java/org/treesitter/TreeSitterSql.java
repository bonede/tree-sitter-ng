
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSql implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sql");
    }
    private native static long tree_sitter_sql();

    private final long ptr;

    public TreeSitterSql() {
        ptr = tree_sitter_sql();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
