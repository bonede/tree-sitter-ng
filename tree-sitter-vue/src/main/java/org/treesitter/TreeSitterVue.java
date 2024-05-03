
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterVue extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-vue");
    }
    private native static long tree_sitter_vue();

    public TreeSitterVue() {
        super(tree_sitter_vue());
    }

    private TreeSitterVue(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterVue(copyPtr());
    }
}
