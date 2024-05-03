
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTablegen extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-tablegen");
    }
    private native static long tree_sitter_tablegen();

    public TreeSitterTablegen() {
        super(tree_sitter_tablegen());
    }

    private TreeSitterTablegen(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTablegen(copyPtr());
    }
}
