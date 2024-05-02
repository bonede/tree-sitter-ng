
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHcl extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-hcl");
    }
    private native static long tree_sitter_hcl();

    public TreeSitterHcl() {
        super(tree_sitter_hcl());
    }

    private TreeSitterHcl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterHcl(copyPtr());
    }
}
