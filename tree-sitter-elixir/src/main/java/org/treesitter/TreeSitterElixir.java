
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElixir implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elixir");
    }
    private native static long tree_sitter_elixir();

    private final long ptr;

    public TreeSitterElixir() {
        ptr = tree_sitter_elixir();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
