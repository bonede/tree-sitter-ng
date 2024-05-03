
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSmali extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-smali");
    }
    private native static long tree_sitter_smali();

    public TreeSitterSmali() {
        super(tree_sitter_smali());
    }

    private TreeSitterSmali(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSmali(copyPtr());
    }
}
