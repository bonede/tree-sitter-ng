
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterObjc extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-objc");
    }
    private native static long tree_sitter_objc();


    public TreeSitterObjc() {
        super(tree_sitter_objc());
    }

    private TreeSitterObjc(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterObjc(copyPtr());
    }
}
