
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterThrift implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-thrift");
    }
    private native static long tree_sitter_thrift();

    private final long ptr;

    public TreeSitterThrift() {
        ptr = tree_sitter_thrift();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
