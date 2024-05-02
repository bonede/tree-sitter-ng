
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterD extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-d");
    }
    private native static long tree_sitter_d();

    public TreeSitterD() {
        super(tree_sitter_d());;
    }

    private TreeSitterD(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterD(copyPtr());
    }
}
