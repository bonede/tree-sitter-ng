
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSwift extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-swift");
    }
    private native static long tree_sitter_swift();

    public TreeSitterSwift() {
        super(tree_sitter_swift());
    }

    private TreeSitterSwift(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSwift(copyPtr());
    }


}
