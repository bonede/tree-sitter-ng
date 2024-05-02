
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterFormula extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-formula");
    }
    private native static long tree_sitter_formula();

    public TreeSitterFormula() {
        super(tree_sitter_formula());
    }

    private TreeSitterFormula(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterFormula(copyPtr());
    }
}
