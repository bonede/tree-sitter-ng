
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTwig extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-twig");
    }
    private native static long tree_sitter_twig();

    public TreeSitterTwig() {
        super(tree_sitter_twig());
    }

    private TreeSitterTwig(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTwig(copyPtr());
    }
}
