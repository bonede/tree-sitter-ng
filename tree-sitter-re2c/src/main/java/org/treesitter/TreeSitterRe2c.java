
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRe2c implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-re2c");
    }
    private native static long tree_sitter_re2c();

    private final long ptr;

    public TreeSitterRe2c() {
        ptr = tree_sitter_re2c();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
