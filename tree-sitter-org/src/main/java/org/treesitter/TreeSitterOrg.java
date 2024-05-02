
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOrg extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-org");
    }
    private native static long tree_sitter_org();

    public TreeSitterOrg() {
        super(tree_sitter_org());
    }

    private TreeSitterOrg(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterOrg(copyPtr());
    }
}
