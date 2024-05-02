
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterEno extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-eno");
    }
    private native static long tree_sitter_eno();

    public TreeSitterEno() {
        super(tree_sitter_eno());;
    }

    private TreeSitterEno(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterEno(copyPtr());
    }
}
