
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSql extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sql");
    }
    private native static long tree_sitter_sql();

    public TreeSitterSql() {
        super(tree_sitter_sql());
    }

    private TreeSitterSql(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSql(copyPtr());
    }


}
