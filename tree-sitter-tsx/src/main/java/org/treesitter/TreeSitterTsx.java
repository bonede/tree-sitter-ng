
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTsx extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-tsx");
    }
    private native static long tree_sitter_tsx();

    public TreeSitterTsx() {
        super(tree_sitter_tsx());
    }

    private TreeSitterTsx(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTsx(copyPtr());
    }
}
