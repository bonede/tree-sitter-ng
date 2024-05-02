
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElixir extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elixir");
    }
    private native static long tree_sitter_elixir();


    public TreeSitterElixir() {
        super(tree_sitter_elixir());
    }

    private TreeSitterElixir(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterElixir(copyPtr());
    }
}
