
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOrg implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-org");
    }
    private native static long tree_sitter_org();

    private final long ptr;

    public TreeSitterOrg() {
        ptr = tree_sitter_org();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
