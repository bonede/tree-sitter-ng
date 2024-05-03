
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRe2c extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-re2c");
    }
    private native static long tree_sitter_re2c();

    public TreeSitterRe2c() {
        super(tree_sitter_re2c());
    }

    private TreeSitterRe2c(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRe2c(copyPtr());
    }
}
