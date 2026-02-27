package org.treesitter;

import java.lang.ref.Cleaner.Cleanable;

import java.util.ArrayList;
import java.util.List;

import static org.treesitter.TSParser.*;

public class TSQuery implements AutoCloseable {
    private final long ptr;
    private TSLanguage lang;
    private List<List<TSQueryPredicate>> predicates;
    private final Cleanable cleanable;
    private boolean closed = false;

    private void ensureOpen() {
        if (closed) {
            throw new IllegalStateException("Query is closed");
        }
    }

    private static class TSQueryCleanRunner implements Runnable {
        private final long ptr;

        public TSQueryCleanRunner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            if (ptr != 0) {
                ts_query_delete(ptr);
            }
        }
    }

    private TSQuery(long ptr) {
        this.ptr = ptr;
        this.cleanable = CleanerRunner.register(this, new TSQueryCleanRunner(ptr));
    }

    @Override
    public void close() {
        closed = true;
        cleanable.clean();
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
        if (ptr == 0) {
            throw new TSQueryException("Syntax error in query: " + query);
        }
        this.lang = language;
        this.predicates = parsePredicates();
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
        ensureOpen();
        return ts_query_pattern_count(ptr);
    }

    /**
     * Get the number of captures.
     *
     * @return The number of captures.
     */
    public int getCaptureCount(){
        ensureOpen();
        return ts_query_capture_count(ptr);
    }

    /**
     * Get the number of strings.
     *
     * @return The number of strings.
     */
    public int getStringCount(){
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
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
        ensureOpen();
        int captureCount = getCaptureCount();
        if(captureId >= captureCount){
            throw new TSException("Invalid capture id: " + captureId);
        }
        return ts_query_capture_name_for_id(ptr, captureId);
    }

    /**
     * Get the predicates for the given pattern.
     *
     * @param patternIndex The index of the pattern.
     * @return The list of predicates for the pattern.
     * @throws IndexOutOfBoundsException if the pattern index is out of bounds.
     */
    public List<TSQueryPredicate> getPredicatesForPattern(int patternIndex) {
        if (patternIndex < 0 || patternIndex >= predicates.size()) {
            throw new IndexOutOfBoundsException("Pattern index " + patternIndex + " is out of bounds");
        }
        return predicates.get(patternIndex);
    }

    private List<List<TSQueryPredicate>> parsePredicates() {
        int patternCount = getPatternCount();
        List<List<TSQueryPredicate>> result = new ArrayList<>(patternCount);
        for (int i = 0; i < patternCount; i++) {
            TSQueryPredicateStep[] steps = getPredicateForPattern(i);
            List<TSQueryPredicate> patternPredicates = new ArrayList<>();
            if (steps == null) {
                result.add(patternPredicates);
                continue;
            }
            int stepIndex = 0;
            while (stepIndex < steps.length) {
                // Find the number of arguments until Done sentinel
                int nargs = 0;
                while (stepIndex + nargs < steps.length &&
                        steps[stepIndex + nargs].getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeDone) {
                    nargs++;
                }

                if (nargs > 0) {
                    TSQueryPredicateStep firstStep = steps[stepIndex];
                    if (firstStep.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
                        throw new TSQueryException("Predicate must begin with a string");
                    }
                    String name = getStringValueForId(firstStep.getValueId());

                    if (TSQueryPredicate.TSQueryPredicateEq.NAMES.contains(name)) {
                        patternPredicates.add(handleEq(name, steps, stepIndex, nargs));
                    } else if (TSQueryPredicate.TSQueryPredicateMatch.NAMES.contains(name)) {
                        patternPredicates.add(handleMatch(name, steps, stepIndex, nargs));
                    } else if (TSQueryPredicate.TSQueryPredicateAnyOf.NAMES.contains(name)) {
                        patternPredicates.add(handleAnyOf(name, steps, stepIndex, nargs));
                    } else if (TSQueryPredicate.TSQueryPredicateSet.NAMES.contains(name)) {
                        patternPredicates.add(handleSet(name, steps, stepIndex, nargs));
                    } else if (TSQueryPredicate.TSQueryPredicateIs.NAMES.contains(name)) {
                        patternPredicates.add(handleIs(name, steps, stepIndex, nargs));
                    } else {
                        patternPredicates.add(new TSQueryPredicate.TSQueryPredicateGeneric(name));
                    }
                }
                stepIndex += nargs + 1; // Move past arguments and the Done sentinel
            }
            result.add(patternPredicates);
        }
        return result;
    }

    private TSQueryPredicate handleEq(String name, TSQueryPredicateStep[] steps, int start, int nargs) {
        if (nargs != 3) {
            throw new TSQueryException(String.format("Predicate #%s expects 2 arguments, got %d", name, nargs - 1));
        }
        TSQueryPredicateStep arg1 = steps[start + 1];
        if (arg1.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeCapture) {
            throw new TSQueryException(String.format("First argument to #%s must be a capture", name));
        }
        int captureId = arg1.getValueId();

        TSQueryPredicateStep arg2 = steps[start + 2];
        int arg2ValueId = arg2.getValueId();
        boolean isCapture = arg2.getType() == TSQueryPredicateStepType.TSQueryPredicateStepTypeCapture;
        String literalValue = isCapture ? null : getStringValueForId(arg2ValueId);

        return new TSQueryPredicate.TSQueryPredicateEq(name, captureId, literalValue, arg2ValueId, isCapture);
    }

    private TSQueryPredicate handleMatch(String name, TSQueryPredicateStep[] steps, int start, int nargs) {
        if (nargs != 3) {
            throw new TSQueryException(String.format("Predicate #%s expects 2 arguments, got %d", name, nargs - 1));
        }
        TSQueryPredicateStep arg1 = steps[start + 1];
        if (arg1.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeCapture) {
            throw new TSQueryException(String.format("First argument to #%s must be a capture", name));
        }
        int captureId = arg1.getValueId();

        TSQueryPredicateStep arg2 = steps[start + 2];
        if (arg2.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
            throw new TSQueryException(String.format("Second argument to #%s must be a string literal", name));
        }
        String patternStr = getStringValueForId(arg2.getValueId());

        return new TSQueryPredicate.TSQueryPredicateMatch(name, captureId, patternStr);
    }

    private TSQueryPredicate handleAnyOf(String name, TSQueryPredicateStep[] steps, int start, int nargs) {
        if (nargs < 3) {
            throw new TSQueryException(String.format("Predicate #%s expects at least 2 arguments, got %d", name, nargs - 1));
        }
        TSQueryPredicateStep arg1 = steps[start + 1];
        if (arg1.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeCapture) {
            throw new TSQueryException(String.format("First argument to #%s must be a capture", name));
        }
        int captureId = arg1.getValueId();

        List<String> values = new ArrayList<>(nargs - 2);
        for (int i = 2; i < nargs; i++) {
            TSQueryPredicateStep arg = steps[start + i];
            if (arg.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
                throw new TSQueryException(String.format("Arguments to #%s must be string literals", name));
            }
            values.add(getStringValueForId(arg.getValueId()));
        }

        return new TSQueryPredicate.TSQueryPredicateAnyOf(name, captureId, values);
    }

    private TSQueryPredicate handleSet(String name, TSQueryPredicateStep[] steps, int start, int nargs) {
        if (nargs != 3) {
            throw new TSQueryException(String.format("Predicate #%s expects 2 arguments, got %d", name, nargs - 1));
        }
        TSQueryPredicateStep arg1 = steps[start + 1];
        if (arg1.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
            throw new TSQueryException(String.format("First argument to #%s must be a string literal (key)", name));
        }
        String key = getStringValueForId(arg1.getValueId());

        TSQueryPredicateStep arg2 = steps[start + 2];
        if (arg2.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
            throw new TSQueryException(String.format("Second argument to #%s must be a string literal (value)", name));
        }
        String value = getStringValueForId(arg2.getValueId());

        return new TSQueryPredicate.TSQueryPredicateSet(name, key, value);
    }

    private TSQueryPredicate handleIs(String name, TSQueryPredicateStep[] steps, int start, int nargs) {
        if (nargs != 3) {
            throw new TSQueryException(String.format("Predicate #%s expects 2 arguments, got %d", name, nargs - 1));
        }
        TSQueryPredicateStep arg1 = steps[start + 1];
        if (arg1.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
            throw new TSQueryException(String.format("First argument to #%s must be a string literal (key)", name));
        }
        String key = getStringValueForId(arg1.getValueId());

        TSQueryPredicateStep arg2 = steps[start + 2];
        if (arg2.getType() != TSQueryPredicateStepType.TSQueryPredicateStepTypeString) {
            throw new TSQueryException(String.format("Second argument to #%s must be a string literal (value)", name));
        }
        String value = getStringValueForId(arg2.getValueId());

        return new TSQueryPredicate.TSQueryPredicateIs(name, key, value);
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
        ensureOpen();
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
     * @throws TSException if the id is invalid.
     */
    /**
     * Get the string value for the given id.
     * @param id the string id.
     * @return the string value.
     */
    public String getStringValueForId(int id) {
        ensureOpen();
        int stringCount = getStringCount();
        if (id < 0 || id >= stringCount) {
            throw new TSException("Invalid string id: " + id);
        }
        return ts_query_string_value_for_id(ptr, id);
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
        ensureOpen();
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
    public void disablePattern(int index) {
        ensureOpen();
        ts_query_disable_pattern(ptr, index);
    }
}
