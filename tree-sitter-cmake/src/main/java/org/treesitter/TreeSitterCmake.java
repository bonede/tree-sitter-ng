
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCmake extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cmake");
    }
    private native static long tree_sitter_cmake();

    public TreeSitterCmake() {
        super(tree_sitter_cmake());
    }

    protected TreeSitterCmake(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCmake(copyPtr());
    }
}
