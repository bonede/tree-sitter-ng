
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNim implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nim");
    }
    private native static long tree_sitter_nim();

    private final long ptr;

    public TreeSitterNim() {
        ptr = tree_sitter_nim();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
