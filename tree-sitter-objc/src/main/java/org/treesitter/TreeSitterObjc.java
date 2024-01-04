
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterObjc implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-objc");
    }
    private native static long tree_sitter_objc();

    private final long ptr;

    public TreeSitterObjc() {
        ptr = tree_sitter_objc();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
