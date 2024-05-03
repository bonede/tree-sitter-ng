
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTypescript extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-typescript");
    }
    private native static long tree_sitter_typescript();

    public TreeSitterTypescript() {
        super(tree_sitter_typescript());
    }

    private TreeSitterTypescript(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTypescript(copyPtr());
    }
}
