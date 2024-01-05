package org.treesitter;

import java.lang.ref.Cleaner;

import static org.treesitter.TSParser.*;

public class TSTreeCursor {
    private static Cleaner cleaner = Cleaner.create();
    private final long ptr;
    static private class TSTreeCursorCleaner implements Runnable {
        private final long ptr;

        public TSTreeCursorCleaner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            ts_tree_cursor_delete(ptr);
            free_cursor(ptr);
        }
    }

    private TSTreeCursor(long ptr) {
        this.ptr = ptr;
        cleaner.register(this, () -> new TSTreeCursorCleaner(ptr));
    }
    /**
     * Create a new tree cursor starting from the given node.<br>
     *
     * A tree cursor allows you to walk a syntax tree more efficiently than is
     * possible using the {@link TSNode} functions. It is a mutable object that is always
     * on a certain syntax node, and can be moved imperatively to different nodes.
     */
    public TSTreeCursor(TSNode node) {
        this(TSParser.ts_tree_cursor_new(node));
    }

    /**
     * Re-initialize a tree cursor to start at a different node.
     */
    public void reset(TSNode node){
        ts_tree_cursor_reset(ptr, node);
    }

    /**
     * Get the tree cursor's current node.
     */
    public TSNode currentNode(){
        return ts_tree_cursor_current_node(ptr);
    }

    /**
     * Get the field name of the tree cursor's current node.<br>
     *
     * This returns <code>null</code> if the current node doesn't have a field.
     * See also {@link TSNode#getChildByFieldName(String) TSNode#getChildByFieldName}.
     */
    public String currentFieldName(){
        return ts_tree_cursor_current_field_name(ptr);
    }
    
    /**
     * Get the field id of the tree cursor's current node.<br>
     *
     * This returns zero if the current node doesn't have a field.
     * See also {@link TSNode#getChildByFieldId(int) TSNode#getChildByFieldId}, {@link TSLanguage#fieldIdForName(String) TSLanguage#fieldIdForName}.
     */
    public int currentFieldId(){
        return ts_tree_cursor_current_field_id(ptr);
    }

    /**
     * Move the cursor to the parent of its current node.<br>
     *
     * This returns <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there was no parent node (the cursor was already on the root node).
     */
    public boolean gotoParent(){
        return ts_tree_cursor_goto_parent(ptr);
    }

    /**
     * Move the cursor to the next sibling of its current node. <br>
     *
     * This returns <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there was no next sibling node.
     */
    public boolean gotoNextSibling(){
        return ts_tree_cursor_goto_next_sibling(ptr);
    }

    /**
     * Move the cursor to the first child of its current node.<br>
     *
     * This returns  <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there were no children.
     */
    public boolean gotoFirstChild(){
        return ts_tree_cursor_goto_first_child(ptr);
    }
    /**
     * Move the cursor to the first child of its current node that extends beyond
     * the given byte offset.<br>
     *
     * This returns the index of the child node if one was found, and returns -1
     * if no such child was found.
     */
    public int gotoFirstChildForByte(int startByte){
        return ts_tree_cursor_goto_first_child_for_byte(ptr, startByte);
    }

    /**
     * Move the cursor to the first child of its current node that extends beyond
     * the given byte point.<br>
     *
     * This returns the index of the child node if one was found, and returns -1
     * if no such child was found.
     */
    public int gotoFirstChildForPoint(TSPoint startPoint){
        return ts_tree_cursor_goto_first_child_for_point(ptr, startPoint);
    }

    public TSTreeCursor copy(){
        return new TSTreeCursor(ts_tree_cursor_copy(ptr));
    }





}
