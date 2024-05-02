
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPhp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-php");
    }
    private native static long tree_sitter_php();

    public TreeSitterPhp() {
       super(tree_sitter_php());
    }

    private TreeSitterPhp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPhp(copyPtr());
    }
}
