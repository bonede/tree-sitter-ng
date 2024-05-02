
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPerl extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-perl");
    }
    private native static long tree_sitter_perl();

    public TreeSitterPerl() {
        super(tree_sitter_perl());
    }

    private TreeSitterPerl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPerl(copyPtr());
    }
}
