
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGoMod implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go-mod");
    }
    private native static long tree_sitter_go_mod();

    private final long ptr;

    public TreeSitterGoMod() {
        ptr = tree_sitter_go_mod();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
