
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHcl implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-hcl");
    }
    private native static long tree_sitter_hcl();

    private final long ptr;

    public TreeSitterHcl() {
        ptr = tree_sitter_hcl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
