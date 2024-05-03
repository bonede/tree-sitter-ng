
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSqlite extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sqlite");
    }
    private native static long tree_sitter_sqlite();

    public TreeSitterSqlite() {
        super(tree_sitter_sqlite());
    }

    private TreeSitterSqlite(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSqlite(copyPtr());
    }
}
