
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTypescript implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-typescript");
    }
    private native static long tree_sitter_typescript();

    private final long ptr;

    public TreeSitterTypescript() {
        ptr = tree_sitter_typescript();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
