
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVerilog extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-verilog");
    }
    private native static long tree_sitter_verilog();

    public TreeSitterVerilog() {
        super(tree_sitter_verilog());
    }

    private TreeSitterVerilog(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterVerilog(copyPtr());
    }
}
