package org.treesitter;

import java.lang.ref.Cleaner;

import static org.treesitter.TSParser.*;

public class TSQuery {
    private final long ptr;
    private static Cleaner cleaner = Cleaner.create();
    static class TSQueryCleaner implements Runnable {
        private final long ptr;

        public TSQueryCleaner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            ts_query_delete(ptr);
        }
    }

    private TSQuery(long ptr) {
        this.ptr = ptr;
        cleaner.register(this, () -> new TSQuery.TSQueryCleaner(ptr));
    }

    public TSQuery(TSLanguage language, String query){
        this(ts_query_new(language.getPtr(), query));
    }

    protected long getPtr() {
        return ptr;
    }

    public int getPatternCount(){
        return ts_query_pattern_count(ptr);
    }

    public int getCaptureCount(){
        return ts_query_capture_count(ptr);
    }

    public int getStringCount(){
        return ts_query_string_count(ptr);
    }

    public int getStartByteForPattern(int patternIndex) {
        return ts_query_start_byte_for_pattern(ptr, patternIndex);
    }

    public TSQueryPredicateStep[] getPredicateForPattern(int patternIndex) {
        return ts_query_predicates_for_pattern(ptr, patternIndex);
    }

    public boolean isPatternRooted(int patternIndex) {
        return ts_query_is_pattern_rooted(ptr, patternIndex);
    }

    public boolean isPatterNonLocal(int patternIndex) {
        return ts_query_is_pattern_non_local(ptr, patternIndex);
    }

    public boolean isPatternGuaranteedAtStep(int byteOffset) {
        return ts_query_is_pattern_guaranteed_at_step(ptr, byteOffset);
    }

    public String getCaptureNameForId(int captureId) {
        int captureCount = getCaptureCount();
        if(captureId >= captureCount){
            throw new TSException("Invalid capture id: " + captureId);
        }
        return ts_query_capture_name_for_id(ptr, captureId);
    }

    public int getCaptureQuantifierForId(int patternId, int captureId) {
        return ts_query_capture_quantifier_for_id(ptr, patternId, captureId);
    }

    public String getStringValueForId(int index) {
        int patternCount = getPatternCount();
        for(int i = 0; i < patternCount; i++){
            TSQueryPredicateStep[] predicates = getPredicateForPattern(i);
            for(int j = 0; j < predicates.length; j++){
                TSQueryPredicateStep predicate = predicates[j];
                if(index == predicate.getValueId() && predicate.getType() == TSQueryPredicateStepType.TSQueryPredicateStepTypeString){
                    return ts_query_string_value_for_id(ptr, predicate.getValueId());
                }
            }
        }
        throw new TSException("Invalid string id: " + index);
    }

    public void disableCapture(String name) {
        ts_query_disable_capture(ptr, name);
    }

    public void disablePattern(int index){
        ts_query_disable_pattern(ptr, index);
    }


}
