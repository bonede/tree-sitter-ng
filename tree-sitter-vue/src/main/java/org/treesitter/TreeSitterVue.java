
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVue implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-vue");
    }
    private native static long tree_sitter_vue();

    private final long ptr;

    public TreeSitterVue() {
        ptr = tree_sitter_vue();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
