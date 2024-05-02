
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGoWork extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go-work");
    }
    private native static long tree_sitter_go_work();

    public TreeSitterGoWork() {
        super(tree_sitter_go_work());
    }

    private TreeSitterGoWork(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGoWork(copyPtr());
    }
}
