
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOhm implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ohm");
    }
    private native static long tree_sitter_ohm();

    private final long ptr;

    public TreeSitterOhm() {
        ptr = tree_sitter_ohm();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
