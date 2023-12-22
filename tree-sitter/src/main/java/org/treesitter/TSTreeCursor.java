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

    public TSTreeCursor(TSNode node) {
        this(TSParser.ts_tree_cursor_new(node));
    }

    public void reset(TSNode node){
        ts_tree_cursor_reset(ptr, node);
    }

    public TSNode currentNode(){
        return ts_tree_cursor_current_node(ptr);
    }

    public String currentFieldName(){
        return ts_tree_cursor_current_field_name(ptr);
    }

    public int currentFieldId(){
        return ts_tree_cursor_current_field_id(ptr);
    }

    public boolean gotoParent(){
        return ts_tree_cursor_goto_parent(ptr);
    }

    public boolean gotoNextSibling(){
        return ts_tree_cursor_goto_next_sibling(ptr);
    }

    public boolean gotoFirstChild(){
        return ts_tree_cursor_goto_first_child(ptr);
    }

    public int gotoFirstChildForByte(int startByte){
        return ts_tree_cursor_goto_first_child_for_byte(ptr, startByte);
    }

    public int gotoFirstChildForPoint(TSPoint startPoint){
        return ts_tree_cursor_goto_first_child_for_point(ptr, startPoint);
    }

    public TSTreeCursor copy(){
        return new TSTreeCursor(ts_tree_cursor_copy(ptr));
    }





}
