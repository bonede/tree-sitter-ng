
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJava implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-java");
    }
    private native static long tree_sitter_java();

    private final long ptr;

    public TreeSitterJava() {
        ptr = tree_sitter_java();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
