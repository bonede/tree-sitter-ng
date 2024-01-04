
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJson5 implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-json5");
    }
    private native static long tree_sitter_json5();

    private final long ptr;

    public TreeSitterJson5() {
        ptr = tree_sitter_json5();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
