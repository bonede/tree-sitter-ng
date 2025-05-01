package org.treesitter;

import static org.treesitter.TSParser.*;

public class TSTreeCursor {
    private final long ptr;
    private TSNode node;

    private static class TSTreeCursorCleanAction implements Runnable {
        private final long ptr;
        public TSTreeCursorCleanAction(long ptr) {
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
        CleanerRunner.register(this, new TSTreeCursorCleanAction(ptr));
    }
    /**
     * Create a new tree cursor starting from the given node.<br>
     *
     * A tree cursor allows you to walk a syntax tree more efficiently than is
     * possible using the {@link TSNode} functions. It is a mutable object that is always
     * on a certain syntax node, and can be moved imperatively to different nodes. <br>
     *
     * Note that the given node is considered the root of the cursor,
     * and the cursor cannot walk outside this node.
     *
     * @param node The node to start the cursor at.
     */
    public TSTreeCursor(TSNode node) {
        this(TSParser.ts_tree_cursor_new(node));
        this.node = node;
    }

    /**
     * Re-initialize a tree cursor to start at the original node that the cursor was
     * constructed with.
     *
     * @param node The node to start the cursor at.
     */
    public void reset(TSNode node){
        ts_tree_cursor_reset(ptr, node);
        this.node = node;
    }

    /**
     * Get the tree cursor's current node.
     *
     * @return The current node.
     */
    public TSNode currentNode(){
        TSNode node = ts_tree_cursor_current_node(ptr);
        node.setTree(this.node.getTree());
        return node;
    }

    /**
     * Get the field name of the tree cursor's current node.<br>
     *
     * This returns <code>null</code> if the current node doesn't have a field.
     * See also {@link TSNode#getChildByFieldName(String) TSNode#getChildByFieldName}.
     *
     * @return The field name of the current node.
     */
    public String currentFieldName(){
        return ts_tree_cursor_current_field_name(ptr);
    }
    
    /**
     * Get the field id of the tree cursor's current node.<br>
     *
     * This returns zero if the current node doesn't have a field.
     * See also {@link TSNode#getChildByFieldId(int) TSNode#getChildByFieldId}, {@link TSLanguage#fieldIdForName(String) TSLanguage#fieldIdForName}.
     *
     * @return The field id of the current node.
     */
    public int currentFieldId(){
        return ts_tree_cursor_current_field_id(ptr);
    }

    /**
     * Move the cursor to the parent of its current node.<br>
     *
     * This returns <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there was no parent node (the cursor was already on the root node). <br>
     *
     * Note that the node the cursor was constructed with is considered the root
     * of the cursor, and the cursor cannot walk outside this node.
     *
     * @return Whether the cursor successfully moved to the parent.
     */
    public boolean gotoParent(){
        return ts_tree_cursor_goto_parent(ptr);
    }

    /**
     * Move the cursor to the next sibling of its current node. <br>
     *
     * Note that the node the cursor was constructed with is considered the root
     * of the cursor, and the cursor cannot walk outside this node.
     *
     * @return <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there was no next sibling node.<br>
     */
    public boolean gotoNextSibling(){
        return ts_tree_cursor_goto_next_sibling(ptr);
    }

    /**
     * Move the cursor to the previous sibling of its current node.<br>
     *
     * Note, that this function may be slower than
     * {@link #gotoNextSibling()} due to how node positions are stored. In
     * the worst case, this will need to iterate through all the children up to the
     * previous sibling node to recalculate its position. Also note that the node the cursor
     * was constructed with is considered the root of the cursor, and the cursor cannot
     * walk outside this node.
     *
     * @return <code>true</code> if the cursor successfully moved, and returns <code>false</code> if
     * there was no previous sibling node.<br>
     */
    public boolean gotoPreviousSibling(){
        return ts_tree_cursor_goto_previous_sibling(ptr);
    }

    /**
     * Move the cursor to the first child of its current node.<br>
     *
     * This returns  <code>true</code> if the cursor successfully moved, and returns <code>false</code>
     * if there were no children.
     *
     * @return Whether the cursor successfully moved to the first child.
     */
    public boolean gotoFirstChild(){
        return ts_tree_cursor_goto_first_child(ptr);
    }

    /**
     * Move the cursor to the first child of its current node that contains or starts after
     * the given byte offset.<br>
     *
     * @param startByte The byte offset.
     *
     * @return The index of the child node if one was found, and returns -1 if no such child was found.
     */
    public int gotoFirstChildForByte(int startByte){
        return ts_tree_cursor_goto_first_child_for_byte(ptr, startByte);
    }

    /**
     * Move the cursor to the first child of its current node that contains or starts after
     * the given byte point.<br>
     *
     * @param startPoint The point offset.
     *
     * @return The index of the child node if one was found, and returns -1 if no such child was found.
     */
    public int gotoFirstChildForPoint(TSPoint startPoint){
        return ts_tree_cursor_goto_first_child_for_point(ptr, startPoint);
    }


    protected void setNode(TSNode node){
        this.node = node;
    }

    public TSTreeCursor copy(){
        TSTreeCursor cursor = new TSTreeCursor(ts_tree_cursor_copy(ptr));
        cursor.setNode(node);
        return cursor;
    }





}
