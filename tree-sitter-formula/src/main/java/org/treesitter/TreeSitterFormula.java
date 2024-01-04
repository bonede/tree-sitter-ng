
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFormula implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-formula");
    }
    private native static long tree_sitter_formula();

    private final long ptr;

    public TreeSitterFormula() {
        ptr = tree_sitter_formula();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
