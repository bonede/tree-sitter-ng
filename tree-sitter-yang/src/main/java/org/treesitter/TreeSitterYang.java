
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterYang extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-yang");
    }
    private native static long tree_sitter_yang();

    public TreeSitterYang() {
        super(tree_sitter_yang());
    }

    public TreeSitterYang(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterYang(copyPtr());
    }
}
