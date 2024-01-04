
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOcaml implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ocaml");
    }
    private native static long tree_sitter_ocaml();

    private final long ptr;

    public TreeSitterOcaml() {
        ptr = tree_sitter_ocaml();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
