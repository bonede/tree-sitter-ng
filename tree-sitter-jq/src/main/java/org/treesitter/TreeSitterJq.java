
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJq implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-jq");
    }
    private native static long tree_sitter_jq();

    private final long ptr;

    public TreeSitterJq() {
        ptr = tree_sitter_jq();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
