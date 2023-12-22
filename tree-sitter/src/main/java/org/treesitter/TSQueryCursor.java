package org.treesitter;

import java.lang.ref.Cleaner;

import static org.treesitter.TSParser.*;

public class TSQueryCursor {
    // TODO refactor this to a global cleaner
    private static Cleaner cleaner = Cleaner.create();
    private final long ptr;
    private boolean executed = false;

    static private class TSQueryCursorCleaner implements Runnable {
        private final long ptr;

        public TSQueryCursorCleaner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            ts_query_cursor_delete(ptr);
        }
    }

    private TSQueryCursor(long ptr) {
        this.ptr = ptr;
        cleaner.register(this, () -> new TSQueryCursorCleaner(ptr));
    }

    public TSQueryCursor() {
        this(TSParser.ts_query_cursor_new());
    }

    public void exec(TSQuery query, TSNode node){
        executed = true;
        ts_query_cursor_exec(ptr, query.getPtr(), node);
    }

    public boolean didExceedMatchLimit(){
        return ts_query_cursor_did_exceed_match_limit(ptr);
    }

    public int getMatchLimit(){
        return ts_query_cursor_match_limit(ptr);
    }

    public void setMatchLimit(int limit){
        ts_query_cursor_set_match_limit(ptr, limit);
    }

    public void setByteRange(int startByte, int endByte){
        ts_query_cursor_set_byte_range(ptr, startByte, endByte);
    }

    public void setPointRange(TSPoint startPoint, TSPoint endPoint){
        ts_query_cursor_set_point_range(ptr, startPoint, endPoint);
    }

    public boolean nextMatch(TSQueryMatch match){
        if(!executed){
            throw new TSException("Query not executed, call exec() first.");
        }
        return ts_query_cursor_next_match(ptr, match);
    }

    public void removeMatch(int matchId){
        ts_query_cursor_remove_match(ptr, matchId);
    }

    public boolean nextCapture(TSQueryMatch match){
        if(!executed){
            throw new TSException("Query not executed, call exec() first.");
        }
        return ts_query_cursor_next_capture(ptr, match);
    }

}
