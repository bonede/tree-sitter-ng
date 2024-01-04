
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSmali implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-smali");
    }
    private native static long tree_sitter_smali();

    private final long ptr;

    public TreeSitterSmali() {
        ptr = tree_sitter_smali();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
