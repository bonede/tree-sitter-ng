
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRego extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rego");
    }
    private native static long tree_sitter_rego();

    public TreeSitterRego() {
        super(tree_sitter_rego());
    }

    private TreeSitterRego(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRego(copyPtr());
    }


}
