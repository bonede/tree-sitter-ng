
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGraphql implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-graphql");
    }
    private native static long tree_sitter_graphql();

    private final long ptr;

    public TreeSitterGraphql() {
        ptr = tree_sitter_graphql();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
