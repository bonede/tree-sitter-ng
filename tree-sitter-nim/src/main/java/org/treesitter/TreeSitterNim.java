
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNim extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nim");
    }
    private native static long tree_sitter_nim();

    public TreeSitterNim() {
        super(tree_sitter_nim());
    }

    private TreeSitterNim(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterNim(copyPtr());
    }
}
