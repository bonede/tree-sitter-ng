
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterM68k implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-m68k");
    }
    private native static long tree_sitter_m68k();

    private final long ptr;

    public TreeSitterM68k() {
        ptr = tree_sitter_m68k();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
