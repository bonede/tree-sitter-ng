
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTact extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-tact");
    }
    private native static long tree_sitter_tact();

    public TreeSitterTact() {
        super(tree_sitter_tact());
    }

    private TreeSitterTact(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTact(copyPtr());
    }
}
