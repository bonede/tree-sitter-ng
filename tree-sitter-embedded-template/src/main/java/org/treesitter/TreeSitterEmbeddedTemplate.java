
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterEmbeddedTemplate implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-embedded-template");
    }
    private native static long tree_sitter_embedded_template();

    private final long ptr;

    public TreeSitterEmbeddedTemplate() {
        ptr = tree_sitter_embedded_template();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
