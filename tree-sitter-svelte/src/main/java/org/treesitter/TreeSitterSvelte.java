
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSvelte extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-svelte");
    }
    private native static long tree_sitter_svelte();

    public TreeSitterSvelte() {
        super(tree_sitter_svelte());
    }

    private TreeSitterSvelte(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSvelte(copyPtr());
    }
}
