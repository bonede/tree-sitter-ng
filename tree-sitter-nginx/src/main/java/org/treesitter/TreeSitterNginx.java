
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterNginx implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-nginx");
    }
    private native static long tree_sitter_nginx();

    private final long ptr;

    public TreeSitterNginx() {
        ptr = tree_sitter_nginx();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
