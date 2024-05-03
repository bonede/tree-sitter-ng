
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSqlBigquery extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sql-bigquery");
    }
    private native static long tree_sitter_sql_bigquery();

    public TreeSitterSqlBigquery() {
        super(tree_sitter_sql_bigquery());
    }

    private TreeSitterSqlBigquery(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSqlBigquery(copyPtr());
    }
}
