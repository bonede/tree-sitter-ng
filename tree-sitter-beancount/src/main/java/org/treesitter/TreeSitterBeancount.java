
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterBeancount implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-beancount");
    }
    private native static long tree_sitter_beancount();

    private final long ptr;

    public TreeSitterBeancount() {
        ptr = tree_sitter_beancount();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
