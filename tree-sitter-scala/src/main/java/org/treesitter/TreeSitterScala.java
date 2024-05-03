
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScala extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scala");
    }
    private native static long tree_sitter_scala();

    public TreeSitterScala() {
        super(tree_sitter_scala());
    }

    private TreeSitterScala(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterScala(copyPtr());
    }
}
