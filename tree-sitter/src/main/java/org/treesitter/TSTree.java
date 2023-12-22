package org.treesitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.Cleaner;

import static org.treesitter.TSParser.*;

public class TSTree {

    static Cleaner cleaner = Cleaner.create();

    private final long ptr;

    static class TSTreeCleaner implements Runnable {
        private final long ptr;

        public TSTreeCleaner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            TSParser.ts_tree_delete(ptr);
        }
    }

    TSTree(long ptr) {
        this.ptr = ptr;
        cleaner.register(this, () -> new TSTreeCleaner(ptr));
    }

    protected long getPtr(){
        return ptr;
    }
    public TSTree copy(){
        return new TSTree(ts_tree_copy(ptr));
    }

    public TSNode getRootNode(){
        return ts_tree_root_node(ptr);
    }

    public TSNode getRootNodeWithOffset(int offsetBytes, TSPoint offsetPoint){
        return ts_tree_root_node_with_offset(ptr, offsetBytes, offsetPoint);
    }

    public TSLanguage getLanguage(){
        return new AnonymousLanguage(ts_tree_language(ptr));
    }

    public TSRange[] getIncludedRanges(){
        return ts_tree_included_ranges(ptr);
    }

    public void edit(TSInputEdit inputEdit){
        ts_tree_edit(ptr, inputEdit);
    }
    public static TSRange[] getChangedRanges(TSTree oldTree, TSTree newTree){
        return ts_tree_get_changed_ranges(oldTree.getPtr(), newTree.getPtr());
    }

    public void printDotGraphs(File file) throws IOException {
        // TODO windows support
        FileOutputStream outputStream = new FileOutputStream(file);
        ts_tree_print_dot_graph(ptr, outputStream.getFD());
        outputStream.close();
    }

}
