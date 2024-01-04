
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSvelte implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-svelte");
    }
    private native static long tree_sitter_svelte();

    private final long ptr;

    public TreeSitterSvelte() {
        ptr = tree_sitter_svelte();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
