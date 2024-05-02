
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGraphql extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-graphql");
    }
    private native static long tree_sitter_graphql();

    public TreeSitterGraphql() {
        super(tree_sitter_graphql());
    }

    private TreeSitterGraphql(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGraphql(copyPtr());
    }
}
