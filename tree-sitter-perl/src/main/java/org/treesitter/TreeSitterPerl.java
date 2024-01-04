
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPerl implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-perl");
    }
    private native static long tree_sitter_perl();

    private final long ptr;

    public TreeSitterPerl() {
        ptr = tree_sitter_perl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
