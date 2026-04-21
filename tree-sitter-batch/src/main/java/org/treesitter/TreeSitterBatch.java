
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterBatch extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-batch");
    }
    private native static long tree_sitter_batch();

    public TreeSitterBatch() {
        super(tree_sitter_batch());
    }

    private TreeSitterBatch(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterBatch(copyPtr());
    }
}
