
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVhdl implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-vhdl");
    }
    private native static long tree_sitter_vhdl();

    private final long ptr;

    public TreeSitterVhdl() {
        ptr = tree_sitter_vhdl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
