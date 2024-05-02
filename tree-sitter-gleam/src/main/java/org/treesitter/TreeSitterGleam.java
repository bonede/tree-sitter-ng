
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGleam extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gleam");
    }
    private native static long tree_sitter_gleam();

    public TreeSitterGleam() {
        super(tree_sitter_gleam());
    }

    private TreeSitterGleam(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGleam(copyPtr());
    }
}
