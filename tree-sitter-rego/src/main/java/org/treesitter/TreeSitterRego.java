
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRego implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rego");
    }
    private native static long tree_sitter_rego();

    private final long ptr;

    public TreeSitterRego() {
        ptr = tree_sitter_rego();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
