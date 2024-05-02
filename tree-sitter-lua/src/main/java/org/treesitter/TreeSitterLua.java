
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLua extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lua");
    }
    private native static long tree_sitter_lua();

    public TreeSitterLua() {
        super(tree_sitter_lua());
    }

    private TreeSitterLua(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLua(copyPtr());
    }
}
