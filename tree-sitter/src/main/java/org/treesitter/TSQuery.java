package org.treesitter;

import static org.treesitter.TSParser.*;

public class TSQuery {
    private final long ptr;

    private static class TSQueryCleanRunner implements Runnable {
        private final long ptr;

        public TSQueryCleanRunner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            ts_query_delete(ptr);
        }
    }

    private TSQuery(long ptr) {
        this.ptr = ptr;
        CleanerRunner.register(this, new TSQueryCleanRunner(ptr));
    }

    /**
     * Create a new query from a string containing one or more S-expression
     * patterns. The query is associated with a particular language, and can
     * only be run on syntax nodes parsed with that language.<br>
     *
     * If all the given patterns are valid, this returns a {@link  TSQuery}.<br>
     * If a pattern is invalid, it throws.
     *
     * @param language The language to which the query is associated
     * @param query The S-expression query
     *
     * @throws TSQueryException If the query is invalid
     */
    public TSQuery(TSLanguage language, String query){
        this(ts_query_new(language.getPtr(), query));
    }

    protected long getPtr() {
        return ptr;
    }
    /**
     * Get the number of patterns.
     *
     * @return The number of patterns.
     */
    public int getPatternCount(){
        return ts_query_pattern_count(ptr);
    }

    /**
     * Get the number of captures.
     *
     * @return The number of captures.
     */
    public int getCaptureCount(){
        return ts_query_capture_count(ptr);
    }

    /**
     * Get the number of strings.
     *
     * @return The number of strings.
     */
    public int getStringCount(){
        return ts_query_string_count(ptr);
    }


    /**
     * Get the byte offset where the given pattern starts in the query's source.<br>
     *
     * This can be useful when combining queries by concatenating their source
     * code strings.
     *
     * @param patternIndex The index of the pattern.
     *
     * @return The byte offset where the pattern starts.
     */
    public int getStartByteForPattern(int patternIndex) {
        return ts_query_start_byte_for_pattern(ptr, patternIndex);
    }

    /**
     * Get the byte offset where the given pattern ends in the query's source.<br>
     *
     * This can be useful when combining queries by concatenating their source
     * code strings.
     *
     * @param patternIndex The index of the pattern.
     * @return The byte offset where the pattern ends.
     */
    public int getEndByteForPattern(int patternIndex) {
        return ts_query_end_byte_for_pattern(ptr, patternIndex);
    }

    /**
     * Get all the predicates for the given pattern in the query.<br>
     *
     * The predicates are represented as a single array of steps. There are three
     * types of steps in this array, which correspond to the three legal values for
     * the <code>type</code> field:
     * <ul>
     * <li> `TSQueryPredicateStepTypeCapture` - Steps with this type represent names
     *    of captures. Their `value_id` can be used with the
     *   `ts_query_capture_name_for_id` function to obtain the name of the capture.</li>
     * <li> `TSQueryPredicateStepTypeString` - Steps with this type represent literal
     *    strings. Their `value_id` can be used with the
     *    `ts_query_string_value_for_id` function to obtain their string value.</li>
     * <li> `TSQueryPredicateStepTypeDone` - Steps with this type are *sentinels*
     *    that represent the end of an individual predicate. If a pattern has two
     *    predicates, then there will be two steps with this `type` in the array.</li>
     * </ul>
     *
     * @param patternIndex The index of the pattern.
     *
     * @return The predicates for the pattern.
     */
    public TSQueryPredicateStep[] getPredicateForPattern(int patternIndex) {
        return ts_query_predicates_for_pattern(ptr, patternIndex);
    }

    /**
     * Check if the given pattern in the query has a single root node.
     *
     * @param patternIndex The index of the pattern.
     *
     * @return True if the pattern has a single root node, false otherwise.
     */
    public boolean isPatternRooted(int patternIndex) {
        return ts_query_is_pattern_rooted(ptr, patternIndex);
    }

    /**
     * Check if the given pattern in the query is 'non-local'.<br>
     *
     * A non-local pattern has multiple root nodes and can match within a
     * repeating sequence of nodes, as specified by the grammar. Non-local
     * patterns disable certain optimizations that would otherwise be possible
     * when executing a query on a specific range of a syntax tree.
     *
     * @param patternIndex The index of the pattern.
     *
     * @return True if the pattern is non-local, false otherwise.
     */
    public boolean isPatterNonLocal(int patternIndex) {
        return ts_query_is_pattern_non_local(ptr, patternIndex);
    }

    /**
     * Check if a given pattern is guaranteed to match once a given step is reached.
     * The step is specified by its byte offset in the query's source code.
     *
     * @param  byteOffset The byte offset in the query's source code.
     *
     * @return True if the pattern is guaranteed to match once the step is reached,
     */
    public boolean isPatternGuaranteedAtStep(int byteOffset) {
        return ts_query_is_pattern_guaranteed_at_step(ptr, byteOffset);
    }


    /**
     * Get the name and length of one of the query's captures, or one of the
     * query's string literals. Each capture and string is associated with a
     * numeric id based on the order that it appeared in the query's source.
     *
     * @param captureId The id of the capture.
     *
     * @return The name of the capture.
     */
    public String getCaptureNameForId(int captureId) {
        int captureCount = getCaptureCount();
        if(captureId >= captureCount){
            throw new TSException("Invalid capture id: " + captureId);
        }
        return ts_query_capture_name_for_id(ptr, captureId);
    }

    /**
     * Get the quantifier of the query's captures. Each capture is * associated
     * with a numeric id based on the order that it appeared in the query's source.
     *
     * @param patternId The id of the pattern.
     * @param captureId The id of the capture.
     *
     * @return The quantifier of the capture.
     */
    public TSQuantifier getCaptureQuantifierForId(int patternId, int captureId) {
        int quantifier = ts_query_capture_quantifier_for_id(ptr, patternId, captureId);
        switch (quantifier){
            case 0: return TSQuantifier.TSQuantifierZero;
            case 1: return TSQuantifier.TSQuantifierZeroOrOne;
            case 2: return TSQuantifier.TSQuantifierZeroOrMore;
            case 3: return TSQuantifier.TSQuantifierOne;
            case 4: return TSQuantifier.TSQuantifierOneOrMore;
            default: throw new TSException("Can't handle quantifier type: %d" + quantifier);
        }
    }

    /**
     * Get TSQueryPredicateStepTypeString by id. See {@link #getPredicateForPattern(int)}
     * @param id the <code>valueId</code> got from {@link #getPredicateForPattern(int)}.
     * @return the literal string value.
     * @throws TSQueryException if the id is invalid.
     */
    public String getStringValueForId(int id) {
        int patternCount = getPatternCount();
        for(int i = 0; i < patternCount; i++){
            TSQueryPredicateStep[] predicates = getPredicateForPattern(i);
            for(int j = 0; j < predicates.length; j++){
                TSQueryPredicateStep predicate = predicates[j];
                if(id == predicate.getValueId() && predicate.getType() == TSQueryPredicateStepType.TSQueryPredicateStepTypeString){
                    return ts_query_string_value_for_id(ptr, predicate.getValueId());
                }
            }
        }
        throw new TSException("Invalid string id: " + id);
    }

    /**
     * Disable a certain capture within a query. <br>
     *
     * This prevents the capture from being returned in matches, and also avoids
     * any resource usage associated with recording the capture. Currently, there
     * is no way to undo this.
     *
     * @param name The name of the capture to disable.
     */
    public void disableCapture(String name) {
        ts_query_disable_capture(ptr, name);
    }

    /**
     * Disable a certain pattern within a query.<br>
     *
     * This prevents the pattern from matching and removes most of the overhead
     * associated with the pattern. Currently, there is no way to undo this.
     *
     * @param index The index of the pattern to disable.
     */
    public void disablePattern(int index){
        ts_query_disable_pattern(ptr, index);
    }

    /**
     * Set the maximum duration in microseconds that query execution should be allowed to
     * take before halting.<br>
     *
     * If query execution takes longer than this, it will halt early, returning null.<br>
     *
     * @see TSQueryCursor#getMatches()
     * @see TSQueryCursor#getCaptures()
     *
     * @param timeoutMicros Timeout in micro seconds.
     */
    public void setTimeoutMicros(long timeoutMicros){
        TSParser.ts_query_cursor_set_timeout_micros(ptr, timeoutMicros);
    }

    /**
     * Get the duration in microseconds that query execution is allowed to take.<br>
     *
     * This is set via {@link TSQuery#setTimeoutMicros(long)}
     *
     * @return Timeout in micro seconds.
     */
    public long getTimeoutMicros(){
        return TSParser.ts_query_cursor_timeout_micros(ptr);
    }



}
