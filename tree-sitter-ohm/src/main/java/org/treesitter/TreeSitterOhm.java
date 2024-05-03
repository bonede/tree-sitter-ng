
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOhm extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ohm");
    }
    private native static long tree_sitter_ohm();

    public TreeSitterOhm() {
        super(tree_sitter_ohm());
    }

    private TreeSitterOhm(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterOhm(copyPtr());
    }
}
