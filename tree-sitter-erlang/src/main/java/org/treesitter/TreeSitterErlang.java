
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterErlang implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-erlang");
    }
    private native static long tree_sitter_erlang();

    private final long ptr;

    public TreeSitterErlang() {
        ptr = tree_sitter_erlang();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
