
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSshClientConfig extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ssh-client-config");
    }
    private native static long tree_sitter_ssh_client_config();


    public TreeSitterSshClientConfig() {
        super(tree_sitter_ssh_client_config());
    }


    private TreeSitterSshClientConfig(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterSshClientConfig(copyPtr());
    }
}
