
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTwig implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-twig");
    }
    private native static long tree_sitter_twig();

    private final long ptr;

    public TreeSitterTwig() {
        ptr = tree_sitter_twig();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
