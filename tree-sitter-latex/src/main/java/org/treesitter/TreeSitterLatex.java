
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLatex extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-latex");
    }
    private native static long tree_sitter_latex();


    public TreeSitterLatex() {
        super(tree_sitter_latex());
    }

    private TreeSitterLatex(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLatex(copyPtr());
    }
}
