
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterToml implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-toml");
    }
    private native static long tree_sitter_toml();

    private final long ptr;

    public TreeSitterToml() {
        ptr = tree_sitter_toml();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
