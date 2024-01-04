
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterWgsl implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-wgsl");
    }
    private native static long tree_sitter_wgsl();

    private final long ptr;

    public TreeSitterWgsl() {
        ptr = tree_sitter_wgsl();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
