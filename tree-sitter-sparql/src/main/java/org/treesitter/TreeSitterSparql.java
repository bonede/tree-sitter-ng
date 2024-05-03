
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSparql extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sparql");
    }
    private native static long tree_sitter_sparql();

    public TreeSitterSparql() {
        super(tree_sitter_sparql());
    }

    private TreeSitterSparql(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSparql(copyPtr());
    }
}
