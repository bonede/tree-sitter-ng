
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJq extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-jq");
    }
    private native static long tree_sitter_jq();

    public TreeSitterJq() {
        super(tree_sitter_jq());
    }

    private TreeSitterJq(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterJq(copyPtr());
    }
}
