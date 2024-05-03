
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterApex extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-apex");
    }
    private native static long tree_sitter_apex();

    public TreeSitterApex() {
        super(tree_sitter_apex());
    }

    private TreeSitterApex(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterApex(copyPtr());
    }


}
