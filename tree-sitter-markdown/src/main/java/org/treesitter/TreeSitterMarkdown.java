
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMarkdown extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-markdown");
    }
    private native static long tree_sitter_markdown();

    public TreeSitterMarkdown() {
        super(tree_sitter_markdown());
    }

    private TreeSitterMarkdown(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterMarkdown(copyPtr());
    }
}
