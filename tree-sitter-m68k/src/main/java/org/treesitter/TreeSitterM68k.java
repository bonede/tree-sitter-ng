
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterM68k extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-m68k");
    }
    private native static long tree_sitter_m68k();


    public TreeSitterM68k() {
        super(tree_sitter_m68k());
    }

    private TreeSitterM68k(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterM68k(copyPtr());
    }
}
