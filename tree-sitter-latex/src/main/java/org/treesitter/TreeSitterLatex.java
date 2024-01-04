
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLatex implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-latex");
    }
    private native static long tree_sitter_latex();

    private final long ptr;

    public TreeSitterLatex() {
        ptr = tree_sitter_latex();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
