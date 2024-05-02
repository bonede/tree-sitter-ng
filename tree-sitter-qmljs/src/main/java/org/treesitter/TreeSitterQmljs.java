
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterQmljs extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-qmljs");
    }
    private native static long tree_sitter_qmljs();

    public TreeSitterQmljs() {
        super(tree_sitter_qmljs());
    }

    private TreeSitterQmljs(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterQmljs(copyPtr());
    }


}
