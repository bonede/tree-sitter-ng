
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWat extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wat");
    }
    private native static long tree_sitter_wat();

    public TreeSitterWat() {
        super(tree_sitter_wat());
    }

    public TreeSitterWat(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterWat(copyPtr());
    }
}
