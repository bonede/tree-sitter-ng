
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWgsl extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wgsl");
    }
    private native static long tree_sitter_wgsl();

    public TreeSitterWgsl() {
        super(tree_sitter_wgsl());
    }

    private TreeSitterWgsl(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterWgsl(copyPtr());
    }
}
