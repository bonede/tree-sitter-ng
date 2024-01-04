
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRuby implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ruby");
    }
    private native static long tree_sitter_ruby();

    private final long ptr;

    public TreeSitterRuby() {
        ptr = tree_sitter_ruby();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
