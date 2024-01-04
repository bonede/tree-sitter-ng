
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCapnp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-capnp");
    }
    private native static long tree_sitter_capnp();

    private final long ptr;

    public TreeSitterCapnp() {
        ptr = tree_sitter_capnp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
