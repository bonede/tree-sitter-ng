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

    /**
     * Create a shallow copy of the syntax tree. This is very fast.<br>
     *
     * You need to copy a syntax tree in order to use it on more than one thread at
     * a time, as syntax trees are not thread safe.
     *
     * @return A copy of the syntax tree.
     */
    public TSTree copy(){
        return new TSTree(ts_tree_copy(ptr));
    }

    /**
     * Get the root node of the syntax tree.
     *
     * @return The root node.
     */
    public TSNode getRootNode(){
        return ts_tree_root_node(ptr);
    }

    /**
     * Get the root node of the syntax tree, but with its position
     * shifted forward by the given offset.
     *
     * @param offsetBytes offset in bytes
     * @param offsetPoint offset in (row, column)
     * @return The node
     */
    public TSNode getRootNodeWithOffset(int offsetBytes, TSPoint offsetPoint){
        return ts_tree_root_node_with_offset(ptr, offsetBytes, offsetPoint);
    }

    /**
     * Get the language that was used to parse the syntax tree.
     * @return The language
     */
    public TSLanguage getLanguage(){
        return new AnonymousLanguage(ts_tree_language(ptr));
    }

    /**
     * Get the array of included ranges that was used to parse the syntax tree.<br>
     *
     * The returned pointer must be freed by the caller.
     *
     * @return The included ranges.
     */
    public TSRange[] getIncludedRanges(){
        return ts_tree_included_ranges(ptr);
    }

    /**
     * Edit the syntax tree to keep it in sync with source code that has been
     * edited.<br>
     *
     * You must describe the edit both in terms of byte offsets and in terms of
     * (row, column) coordinates.
     *
     * @param inputEdit The edit to apply
     */
    public void edit(TSInputEdit inputEdit){
        ts_tree_edit(ptr, inputEdit);
    }

    /**
     * Compare an old edited syntax tree to a new syntax tree representing the same
     * document, returning an array of ranges whose syntactic structure has changed.<br>
     *
     * For this to work correctly, the old syntax tree must have been edited such
     * that its ranges match up to the new tree. Generally, you'll want to call
     * this function right after calling one of the {@link TSParser#parse(byte[], TSTree, TSReader, TSInputEncoding) parse()} functions.
     * You need to pass the old tree that was passed to parse, as well as the new
     * tree that was returned from that function.<br>
     *
     * @param oldTree The old syntax tree
     * @param newTree The new syntax tree
     * @return The changed ranges.
     *
     */
    public static TSRange[] getChangedRanges(TSTree oldTree, TSTree newTree){
        return ts_tree_get_changed_ranges(oldTree.getPtr(), newTree.getPtr());
    }

    /**
     * Write a DOT graph describing the syntax tree to the given file.<br>
     *
     * NOTE: This is function is not implemented on Windows. <a href="https://github.com/tree-sitter/tree-sitter/blob/660481dbf71413eba5a928b0b0ab8da50c1109e0/lib/src/tree.c#L128">Reference</a>
     *
     * @param file The file to write to.
     *
     * @throws IOException If the file could not be written to.
     */
    public void printDotGraphs(File file) throws IOException {
        // TODO add Windows support
        FileOutputStream outputStream = new FileOutputStream(file);
        ts_tree_print_dot_graph(ptr, outputStream.getFD());
        outputStream.close();
    }

}
