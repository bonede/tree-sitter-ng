
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVhdl extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-vhdl");
    }
    private native static long tree_sitter_vhdl();

    public TreeSitterVhdl() {
        super(tree_sitter_vhdl());
    }

    private TreeSitterVhdl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterVhdl(copyPtr());
    }
}
