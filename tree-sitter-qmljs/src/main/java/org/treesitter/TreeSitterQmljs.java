
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterQmljs implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-qmljs");
    }
    private native static long tree_sitter_qmljs();

    private final long ptr;

    public TreeSitterQmljs() {
        ptr = tree_sitter_qmljs();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
