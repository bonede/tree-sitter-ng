
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterElm extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-elm");
    }
    private native static long tree_sitter_elm();

    public TreeSitterElm() {
        super(tree_sitter_elm());
    }

    private TreeSitterElm(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterElm(copyPtr());
    }
}
