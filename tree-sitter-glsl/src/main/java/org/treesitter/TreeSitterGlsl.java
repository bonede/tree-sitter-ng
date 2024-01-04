
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGlsl implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-glsl");
    }
    private native static long tree_sitter_glsl();

    private final long ptr;

    public TreeSitterGlsl() {
        ptr = tree_sitter_glsl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
