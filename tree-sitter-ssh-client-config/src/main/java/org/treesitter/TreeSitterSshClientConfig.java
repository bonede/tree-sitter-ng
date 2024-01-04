
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterSshClientConfig implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ssh-client-config");
    }
    private native static long tree_sitter_ssh_client_config();

    private final long ptr;

    public TreeSitterSshClientConfig() {
        ptr = tree_sitter_ssh_client_config();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
