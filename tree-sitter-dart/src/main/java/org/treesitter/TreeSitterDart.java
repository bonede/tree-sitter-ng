
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDart extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dart");
    }
    private native static long tree_sitter_dart();

    public TreeSitterDart() {
        super(tree_sitter_dart());
    }

    private TreeSitterDart(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterDart(copyPtr());
    }
}
