
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSparql implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-sparql");
    }
    private native static long tree_sitter_sparql();

    private final long ptr;

    public TreeSitterSparql() {
        ptr = tree_sitter_sparql();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
