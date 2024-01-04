
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCSharp implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-c-sharp");
    }
    private native static long tree_sitter_c_sharp();

    private final long ptr;

    public TreeSitterCSharp() {
        ptr = tree_sitter_c_sharp();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
