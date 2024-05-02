
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterComment extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-comment");
    }
    private native static long tree_sitter_comment();

    public TreeSitterComment() {
        super(tree_sitter_comment());
    }

    public TreeSitterComment(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterComment(copyPtr());
    }
}
