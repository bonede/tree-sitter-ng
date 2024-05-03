
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRasi extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-rasi");
    }
    private native static long tree_sitter_rasi();

    public TreeSitterRasi() {
        super(tree_sitter_rasi());
    }

    private TreeSitterRasi(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRasi(copyPtr());
    }


}
