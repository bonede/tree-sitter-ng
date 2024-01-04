
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSqlBigquery implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sql-bigquery");
    }
    private native static long tree_sitter_sql_bigquery();

    private final long ptr;

    public TreeSitterSqlBigquery() {
        ptr = tree_sitter_sql_bigquery();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
