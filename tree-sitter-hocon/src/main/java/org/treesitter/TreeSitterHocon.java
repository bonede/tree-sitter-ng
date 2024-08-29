package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHocon extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-hocon");
    }

    private native static long tree_sitter_hocon();

    public TreeSitterHocon() {
        super(tree_sitter_hocon());
    }

    private TreeSitterHocon(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterHocon(copyPtr());
    }
}
