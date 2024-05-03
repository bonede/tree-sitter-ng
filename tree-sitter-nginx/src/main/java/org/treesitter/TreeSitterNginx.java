
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNginx extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nginx");
    }
    private native static long tree_sitter_nginx();

    public TreeSitterNginx() {
        super(tree_sitter_nginx());
    }

    private TreeSitterNginx(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterNginx(copyPtr());
    }
}
