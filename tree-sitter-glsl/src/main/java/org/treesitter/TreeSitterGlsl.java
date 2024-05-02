
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGlsl extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-glsl");
    }
    private native static long tree_sitter_glsl();


    public TreeSitterGlsl() {
        super(tree_sitter_glsl());
    }

    private TreeSitterGlsl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGlsl(copyPtr());
    }
}
