
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVerilog implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-verilog");
    }
    private native static long tree_sitter_verilog();

    private final long ptr;

    public TreeSitterVerilog() {
        ptr = tree_sitter_verilog();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
