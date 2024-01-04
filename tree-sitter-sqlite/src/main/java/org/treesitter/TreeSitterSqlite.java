
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSqlite implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sqlite");
    }
    private native static long tree_sitter_sqlite();

    private final long ptr;

    public TreeSitterSqlite() {
        ptr = tree_sitter_sqlite();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
