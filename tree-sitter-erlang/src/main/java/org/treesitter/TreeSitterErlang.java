
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterErlang extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-erlang");
    }
    private native static long tree_sitter_erlang();

    public TreeSitterErlang() {
        super(tree_sitter_erlang());
    }

    private TreeSitterErlang(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterErlang(copyPtr());
    }
}
