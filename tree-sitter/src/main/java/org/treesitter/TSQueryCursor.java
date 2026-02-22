package org.treesitter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import static org.treesitter.TSParser.*;
import static org.treesitter.TSParser.ts_query_cursor_next_match;

public class TSQueryCursor implements AutoCloseable {

    private final long ptr;
    private final long progressPayloadPtr;
    private final java.lang.ref.Cleaner.Cleanable cleanable;
    private boolean executed = false;

    private TSNode node;
    private TSQuery query;

    private static class TSQueryCursorCleanAction implements Runnable {
        private final long ptr;
        private final long optionsPtr;

        public TSQueryCursorCleanAction(long ptr, long optionsPtr) {
            this.ptr = ptr;
            this.optionsPtr = optionsPtr;
        }

        @Override
        public void run() {
            ts_query_cursor_delete(ptr);
            ts_query_cursor_options_delete(optionsPtr);
        }
    }

    private TSQueryCursor(long ptr, long progressPayloadPtr) {
        this.ptr = ptr;
        this.progressPayloadPtr = progressPayloadPtr;
        this.cleanable = CleanerRunner.register(this, new TSQueryCursorCleanAction(ptr, progressPayloadPtr));
    }

    @Override
    public void close() {
        cleanable.clean();
    }

    /**
     * Create a new cursor for executing a given query.<br>
     *
     * The cursor stores the state that is needed to iteratively search
     * for matches. To use the query cursor, first call {@link #exec(TSQuery, TSNode) exec()}
     * to start running a given query on a given syntax node. Then, there are
     * two options for consuming the results of the query:
     * <ol>
     * <li> Repeatedly call {@link #nextMatch(TSQueryMatch) nextMatch()} to iterate over all of the
     *    *matches* in the order that they were found. Each match contains the
     *    index of the pattern that matched, and an array of captures. Because
     *    multiple patterns can match the same set of nodes, one match may contain
     *    captures that appear *before* some of the captures from a previous match.</li>
     * <li> Repeatedly call {@link #nextCapture(TSQueryMatch) nextCapture()} to iterate over all of the
     *    individual *captures* in the order that they appear. This is useful if
     *    don't care about which pattern matched, and just want a single ordered
     *    sequence of captures.</li>
     * </ol>
     * If you don't care about consuming all the results, you can stop calling
     *  {@link #nextMatch(TSQueryMatch) nextMatch()} or {@link #nextCapture(TSQueryMatch) nextCapture()} at any point.
     *  You can then start executing another query on another node by calling
     *  {@link #exec(TSQuery, TSNode) exec()} again.
     */
    public TSQueryCursor() {
        this(TSParser.ts_query_cursor_new(), TSParser.ts_query_cursor_options_new());
    }

    /**
     * Start running a given query on a given node.
     *
     * @param query The query to run.
     * @param node The node to run the query on.
     */
    public void exec(TSQuery query, TSNode node){
        executed = true;
        this.node = node;
        this.query = query;
        ts_query_cursor_exec(ptr, query.getPtr(), node);
    }

    /**
     * Start running a given query on a given node, with some options.
     *
     * @see #exec(TSQuery, TSNode)
     * @param query The query to run.
     * @param node The node to run the query on.
     * @param progress The progress callback.
     */
    public void execWithOptions(TSQuery query, TSNode node, TSQueryProgress progress){
        executed = true;
        this.node = node;
        this.query = query;
        ts_query_cursor_exec_with_options(ptr, query.getPtr(), node, progress, progressPayloadPtr);
    }


    public boolean didExceedMatchLimit(){
        return ts_query_cursor_did_exceed_match_limit(ptr);
    }

    public int getMatchLimit(){
        return ts_query_cursor_match_limit(ptr);
    }

    /**
     * Set the maximum number of in-progress matches allowed by this query
     * cursor.<br>
     *
     * Query cursors have an optional maximum capacity for storing lists of
     * in-progress captures. If this capacity is exceeded, then the
     * earliest-starting match will silently be dropped to make room for further
     * matches. This maximum capacity is optional by default, query cursors allow
     * any number of pending matches, dynamically allocating new space for them as
     * needed as the query is executed.
     *
     * @param limit The maximum number of in-progress matches allowed by this query cursor.
     */
    public void setMatchLimit(int limit){
        ts_query_cursor_set_match_limit(ptr, limit);
    }

    /**
     * Set the range of bytes in which the query will be executed.<br>
     *
     * The query cursor will return matches that intersect with the given point range.
     * This means that a match may be returned even if some of its captures fall
     * outside the specified range, as long as at least part of the match
     * overlaps with the range.<br>
     *
     * For example, if a query pattern matches a node that spans a larger area
     * than the specified range, but part of that node intersects with the range,
     * the entire match will be returned.<br>
     *
     *
     * @param startByte  The index of the start byte in the range.
     * @param endByte    The index of the end byte in the range.
     * @return <code>false</code> if the start byte is greater than the end byte, otherwise it will return <code>true</code>.
     */
    public boolean setByteRange(int startByte, int endByte){
        return ts_query_cursor_set_byte_range(ptr, startByte, endByte);
    }

    /**
     * Set the range of (row, column) positions in which the query will be executed.<br>
     *
     * The query cursor will return matches that intersect with the given point range.
     * This means that a match may be returned even if some of its captures fall
     * outside the specified range, as long as at least part of the match
     * overlaps with the range.<br>
     *
     * For example, if a query pattern matches a node that spans a larger area
     * than the specified range, but part of that node intersects with the range,
     * the entire match will be returned.<br>
     *
     * This will return `false` if the start point is greater than the end point, otherwise
     * it will return `true`.
     *
     * @param startPoint  The start point of the range.
     * @param endPoint    The end point of the range.
     * @return <code>false</code> if the start point is greater than the end point, otherwise it will return <code>true</code>.
     */
    public boolean setPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_query_cursor_set_point_range(ptr, startPoint, endPoint);
    }

    /**
     * Set the byte range within which all matches must be fully contained. <br>
     *
     * Set the range of bytes in which matches will be searched for. In contrast to
     * {@link #setByteRange(int, int)}, this will restrict the query cursor to only return
     * matches where _all_ nodes are _fully_ contained within the given range. Both functions
     * can be used together, e.g. to search for any matches that intersect line 5000, as
     * long as they are fully contained within lines 4500-5500
     *
     * @param startByte The start byte of the containing range.
     * @param endByte The end byte of the containing range.
     * @return <code>false</code> if the start byte is greater than the end byte, otherwise it will return <code>true</code>.
     */
    public boolean setContainingByteRange(int startByte, int endByte){
        return ts_query_cursor_set_containing_byte_range(ptr, startByte, endByte);
    }

    /**
     * Set the point range within which all matches must be fully contained.<br>
     *
     * Set the range of bytes in which matches will be searched for. In contrast to
     * {@link #setPointRange(TSPoint, TSPoint)}, this will restrict the query cursor to only return
     * matches where _all_ nodes are _fully_ contained within the given range. Both functions
     * can be used together, e.g. to search for any matches that intersect line 5000, as
     * long as they are fully contained within lines 4500-5500
     *
     * @param startPoint The start point of the containing range.
     * @param endPoint The end point of the containing range.
     * @return <code>false</code> if the start point is greater than the end point, otherwise it will return <code>true</code>.
     */
    public boolean setContainingPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_query_cursor_set_containing_point_range(ptr, startPoint, endPoint);
    }

    /**
     * Advance to the next match of the currently running query.<br>
     *
     * If there is a match, write it to <code>match</code> and return <code>true</code>.
     * Otherwise, return <code>false</code>. <br>
     *
     * NOTE: More Java-ish method is {@link TSQueryCursor#getMatches()}.
     *
     * @param match The match to write to.
     *
     * @return Whether there was a match.
     *
     * @throws TSException if the query has not been executed yet.
     */
    public boolean nextMatch(TSQueryMatch match){
        assertExecuted();
        boolean ret = ts_query_cursor_next_match(ptr, match);
        addTsTreeRef(match);
        return ret;
    }


    public void removeMatch(int matchId){
        ts_query_cursor_remove_match(ptr, matchId);
    }

    /**
     * Advance to the next capture of the currently running query.<br>
     *
     * If there is a capture, write its match to <code>match</code> and its index within
     * the match's capture list to <code>captureIndex</code>. Otherwise, return <code>false</code>. <br>
     *
     * NOTE: More Java-ish method is {@link TSQueryCursor#getCaptures()}.
     *
     * @param match The match to write to.
     *
     * @return Whether there was a capture.
     *
     * @throws TSException if the query has not been executed yet.
     *
     */
    public boolean nextCapture(TSQueryMatch match){
        assertExecuted();
        boolean ret = ts_query_cursor_next_capture(ptr, match);
        addTsTreeRef(match);
        return ret;
    }

    private void addTsTreeRef(TSQueryMatch match){
        if(match.getCaptures() != null){
            for (TSQueryCapture capture : match.getCaptures()) {
                if(capture.getNode() != null){
                    capture.getNode().setTree(this.node.getTree());
                }
            }
        }
    }

    private void assertExecuted(){
        if(!executed){
            throw new TSException("Query not executed, call exec() first.");
        }
    }

    /**
     * Get the match iterator.<br>
     *
     * @return An iterator over the matches.
     *
     */
    public TSMatchIterator getMatches(){
        return new TSMatchIterator(this, false);
    }

    /**
     * Get the capture iterator.<br>
     *
     * @return An iterator over the captures.
     *
     */
    public TSMatchIterator getCaptures(){
        return new TSMatchIterator(this, true);
    }

    public static class TSMatchIterator implements Iterator<TSQueryMatch>{
        private TSQueryMatch lastMatch = null;
        private TSQueryMatch hasNextTempMatch = null;
        private final TSQueryCursor cursor;
        private final boolean isCapture;
        public TSMatchIterator(TSQueryCursor cursor, boolean isCapture) {
            this.cursor = cursor;
            this.isCapture = isCapture;
        }

        @Override
        public boolean hasNext() {
            if(hasNextTempMatch != null){
                return true;
            }
            cursor.assertExecuted();
            TSQueryMatch match = new TSQueryMatch();
            boolean hasNext = isCapture ? cursor.nextCapture(match) : cursor.nextMatch(match);
            if(hasNext){
                hasNextTempMatch = match;
            }
            return hasNext;
        }

        @Override
        public TSQueryMatch next() {
            if(hasNextTempMatch != null){
                TSQueryMatch lastMatch = hasNextTempMatch;
                hasNextTempMatch = null;
                return lastMatch;
            }
            cursor.assertExecuted();
            TSQueryMatch match = new TSQueryMatch();
            boolean hasNext = isCapture ? cursor.nextCapture(match) : cursor.nextMatch(match);
            if(hasNext){
                lastMatch = match;
                return match;
            }else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if(lastMatch == null){
                throw new IllegalStateException();
            }
            ts_query_cursor_remove_match(cursor.ptr, lastMatch.getId());
        }
    }

}
