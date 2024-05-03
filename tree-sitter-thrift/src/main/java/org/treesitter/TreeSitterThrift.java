
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterThrift extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-thrift");
    }
    private native static long tree_sitter_thrift();

    public TreeSitterThrift() {
        super(tree_sitter_thrift());
    }

    private TreeSitterThrift(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterThrift(copyPtr());
    }

}
