
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterBeancount extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-beancount");
    }
    private native static long tree_sitter_beancount();

    public TreeSitterBeancount() {
        super(tree_sitter_beancount());
    }

    private TreeSitterBeancount(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterBeancount(copyPtr());
    }
}
