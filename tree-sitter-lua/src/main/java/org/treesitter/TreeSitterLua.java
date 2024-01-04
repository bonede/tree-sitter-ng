
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLua implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lua");
    }
    private native static long tree_sitter_lua();

    private final long ptr;

    public TreeSitterLua() {
        ptr = tree_sitter_lua();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
