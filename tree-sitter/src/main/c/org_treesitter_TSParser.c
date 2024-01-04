#include <jni.h>
#include <tree_sitter/api.h>
#include <stdio.h>

// TODO cache field, method at startup
#define TS_NODE_CLASS_NAME "org/treesitter/TSNode"
#define TS_NODE_SIGNATURE "Lorg/treesitter/TSNode;"
#define TS_POINT_CLASS_NAME "org/treesitter/TSPoint"
#define TS_POINT_SIGNATURE "Lorg/treesitter/TSPoint;"
#define TS_RANGE_CLASS_NAME "org/treesitter/TSRange"
#define TS_LOG_TYPE_CLASS_NAME "org/treesitter/TSLogType"
#define TS_LOG_TYPE_SIGNATURE "Lorg/treesitter/TSLogType;"
#define TS_READER_READ_METHOD_SIGNATURE "([BILorg/treesitter/TSPoint;)I"
#define TS_LOGGER_LOG_METHOD_SIGNATURE "(Lorg/treesitter/TSLogType;Ljava/lang/String;)V"
#define TS_QUERY_EXCEPTION_CLASS_NAME "org/treesitter/TSQueryException"
#define TS_QUERY_PREDICATE_STEP_CLASS_NAME "org/treesitter/TSQueryPredicateStep"
#define TS_QUERY_PREDICATE_STEP_TYPE_CLASS_NAME "org/treesitter/TSQueryPredicateStepType"
#define TS_QUERY_PREDICATE_STEP_TYPE_SIGNATURE "Lorg/treesitter/TSQueryPredicateStepType;"
#define TS_QUERY_QUERY_CAPTURE_CLASS_NAME "org/treesitter/TSQueryCapture"
#define TS_QUERY_QUERY_CAPTURE_ARRAY_SIGNATURE "[Lorg/treesitter/TSQueryCapture;"

jclass ts_jni_find_class(JNIEnv *env, const char *class_name){
    return (*env)->FindClass(env, class_name);
}

JavaVM* java_vm = NULL;

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    java_vm = vm;
    return JNI_VERSION_10;
}

jfieldID ts_jni_get_field_id(JNIEnv *env, jclass clz, const char *field_name, const char*field_type){
    return (*env)->GetFieldID(env, clz, field_name, field_type);
}

jobject ts_node_to_obj(JNIEnv *env, TSNode ts_node){
    jclass ts_node_class = ts_jni_find_class(env, TS_NODE_CLASS_NAME);
    jobject ts_node_object = (*env)->AllocObject(env, ts_node_class);
    (*env)->SetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context0", "I"), ts_node.context[0]);
    (*env)->SetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context1", "I"), ts_node.context[1]);
    (*env)->SetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context2", "I"), ts_node.context[2]);
    (*env)->SetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context3", "I"), ts_node.context[3]);
    (*env)->SetLongField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "idPtr", "J"), (jlong) ts_node.id);
    (*env)->SetLongField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "treePtr", "J"), (jlong) ts_node.tree);
    return ts_node_object;
}

TSNode ts_node_from_obj(JNIEnv *env, jobject ts_node_object){
    TSNode ts_node;
    jclass ts_node_class = (*env)->GetObjectClass(env, ts_node_object);
    ts_node.context[0] = (uint32_t)(*env)->GetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context0", "I"));
    ts_node.context[1] = (uint32_t)(*env)->GetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context1", "I"));
    ts_node.context[2] = (uint32_t)(*env)->GetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context2", "I"));
    ts_node.context[3] = (uint32_t)(*env)->GetIntField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "context3", "I"));
    ts_node.id = (void *)(*env)->GetLongField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "idPtr", "J"));
    ts_node.tree = (const TSTree*)(*env)->GetLongField(env, ts_node_object, ts_jni_get_field_id(env, ts_node_class, "treePtr", "J"));
    return ts_node;
}

jobject ts_point_to_obj(JNIEnv *env, TSPoint ts_point){
    jclass ts_point_class = ts_jni_find_class(env, TS_POINT_CLASS_NAME);
    jobject ts_point_object = (*env)->AllocObject(env, ts_point_class);
    (*env)->SetIntField(env, ts_point_object, ts_jni_get_field_id(env, ts_point_class, "row", "I"), ts_point.row);
    (*env)->SetIntField(env, ts_point_object, ts_jni_get_field_id(env, ts_point_class, "column", "I"), ts_point.column);
    return ts_point_object;
}

TSPoint ts_point_from_obj(JNIEnv *env, jobject ts_point_obj){
    TSPoint ts_point;
    jclass ts_point_class = (*env)->GetObjectClass(env, ts_point_obj);
    ts_point.row = (uint32_t)(*env)->GetIntField(env, ts_point_obj, ts_jni_get_field_id(env, ts_point_class, "row", "I"));
    ts_point.column = (uint32_t)(*env)->GetIntField(env, ts_point_obj, ts_jni_get_field_id(env, ts_point_class, "column", "I"));
    return ts_point;
}

jobject ts_range_to_obj(JNIEnv *env, const TSRange *ts_range){
    jclass ts_range_class = ts_jni_find_class(env, TS_RANGE_CLASS_NAME);
    jobject ts_range_object = (*env)->AllocObject(env, ts_range_class);
    jobject startPoint = ts_point_to_obj(env, ts_range->start_point);
    jobject endPoint = ts_point_to_obj(env, ts_range->end_point);
    (*env)->SetObjectField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "startPoint", TS_POINT_SIGNATURE), startPoint);
    (*env)->SetObjectField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "endPoint", TS_POINT_SIGNATURE), endPoint);
    (*env)->SetIntField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "startByte", "I"), ts_range->start_byte);
    (*env)->SetIntField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "endByte", "I"), ts_range->end_byte);
    return ts_range_object;
}

jobject predicate_step_type_from_num(JNIEnv *env, TSQueryPredicateStepType type){
    jclass step_type_class = (*env)->FindClass(env, TS_QUERY_PREDICATE_STEP_TYPE_CLASS_NAME);
    jfieldID enum_field;
    switch(type){
        case TSQueryPredicateStepTypeDone:
            enum_field = (*env)->GetStaticFieldID(env, step_type_class , "TSQueryPredicateStepTypeDone", TS_QUERY_PREDICATE_STEP_TYPE_SIGNATURE);
            break;
        case TSQueryPredicateStepTypeCapture:
            enum_field = (*env)->GetStaticFieldID(env, step_type_class , "TSQueryPredicateStepTypeCapture", TS_QUERY_PREDICATE_STEP_TYPE_SIGNATURE);
            break;
        case TSQueryPredicateStepTypeString:
            enum_field = (*env)->GetStaticFieldID(env, step_type_class , "TSQueryPredicateStepTypeString", TS_QUERY_PREDICATE_STEP_TYPE_SIGNATURE);
            break;
        default:
            (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/RuntimeException"), "Can't handle TSQueryPredicateStepType");
    }
    return (* env)->GetStaticObjectField(env, step_type_class, enum_field);
}




jobject ts_query_predicate_step_to_obj(JNIEnv *env, const TSQueryPredicateStep *step){
    jclass ts_query_predicate_step_class = ts_jni_find_class(env, TS_QUERY_PREDICATE_STEP_CLASS_NAME);
    jclass ts_query_predicate_step_type_class = ts_jni_find_class(env, TS_QUERY_PREDICATE_STEP_TYPE_CLASS_NAME);
    jobject step_object = (*env)->AllocObject(env, ts_query_predicate_step_class);
    (*env)->SetObjectField(env, step_object, ts_jni_get_field_id(env, ts_query_predicate_step_class, "type", TS_QUERY_PREDICATE_STEP_TYPE_SIGNATURE), predicate_step_type_from_num(env, step->type));
    (*env)->SetIntField(env, step_object, ts_jni_get_field_id(env, ts_query_predicate_step_class, "valueId", "I"), step->value_id);
    return step_object;
}

void ts_range_from_obj(JNIEnv *env, jobject ts_range_object, TSRange *ts_range){
    jclass ts_range_class = (*env)->GetObjectClass(env, ts_range_object);
    ts_range->start_point = ts_point_from_obj(env, (*env)->GetObjectField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "startPoint", TS_POINT_SIGNATURE)));
    ts_range->end_point = ts_point_from_obj(env, (*env)->GetObjectField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "endPoint", TS_POINT_SIGNATURE)));
    ts_range->start_byte = (uint32_t)(*env)->GetIntField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "startByte", "I"));
    ts_range->end_byte = (uint32_t)(*env)->GetIntField(env, ts_range_object, ts_jni_get_field_id(env, ts_range_class, "endByte", "I"));
}

void ts_input_edit_from_obj(JNIEnv *env, jobject ts_input_edit_object, TSInputEdit *edit){
    jclass ts_input_edit_class = (*env)->GetObjectClass(env, ts_input_edit_object);
    edit->start_byte = (uint32_t)(*env)->GetIntField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "startByte", "I"));
    edit->old_end_byte = (uint32_t)(*env)->GetIntField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "oldEndByte", "I"));
    edit->new_end_byte = (uint32_t)(*env)->GetIntField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "newEndByte", "I"));
    edit->start_point = ts_point_from_obj(env, (*env)->GetObjectField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "startPoint", TS_POINT_SIGNATURE)));
    edit->old_end_point = ts_point_from_obj(env, (*env)->GetObjectField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "oldEndPoint", TS_POINT_SIGNATURE)));
    edit->new_end_point = ts_point_from_obj(env, (*env)->GetObjectField(env, ts_input_edit_object, ts_jni_get_field_id(env, ts_input_edit_class, "newEndPoint", TS_POINT_SIGNATURE)));
}

jobject ts_query_capture_to_obj(JNIEnv *env, const TSQueryCapture *capture){
    jclass ts_capture_class = ts_jni_find_class(env, TS_QUERY_QUERY_CAPTURE_CLASS_NAME);
    jobject ts_capture_object = (*env)->AllocObject(env, ts_capture_class);
    (*env)->SetIntField(env, ts_capture_object, ts_jni_get_field_id(env, ts_capture_class, "index", "I"), capture->index);
    (*env)->SetObjectField(env, ts_capture_object, ts_jni_get_field_id(env, ts_capture_class, "node", TS_NODE_SIGNATURE), ts_node_to_obj(env, capture->node));
    return ts_capture_object;
}

void copy_match(JNIEnv *env, TSQueryMatch *match, int capture_index, jobject ts_match_obj){
   jclass match_class_name = (*env)->GetObjectClass(env, ts_match_obj);
   jclass match_capture_name = (*env)->FindClass(env, TS_QUERY_QUERY_CAPTURE_CLASS_NAME);
   jclass ts_capture_class = ts_jni_find_class(env, TS_QUERY_QUERY_CAPTURE_CLASS_NAME);
   (*env)->SetIntField(env, ts_match_obj, ts_jni_get_field_id(env, match_class_name, "id", "I"), match->id);
   (*env)->SetIntField(env, ts_match_obj, ts_jni_get_field_id(env, match_class_name, "patternIndex", "I"), match->pattern_index);
   (*env)->SetIntField(env, ts_match_obj, ts_jni_get_field_id(env, match_class_name, "captureIndex", "I"), capture_index);
   jobjectArray capture_objects = (*env)->NewObjectArray(env, match->capture_count, ts_capture_class, NULL);
   (*env)->SetObjectField(env, ts_match_obj, ts_jni_get_field_id(env, match_class_name, "captures", TS_QUERY_QUERY_CAPTURE_ARRAY_SIGNATURE), capture_objects);
   for(int i = 0; i < match->capture_count; i++){
       (*env)->SetObjectArrayElement(env, capture_objects, i, ts_query_capture_to_obj(env, match->captures));
       match->captures++;
   }
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_new
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1new
  (JNIEnv *env, jclass clz){
    return (jlong) ts_parser_new();
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_set_language
 * Signature: (JJ)J
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1set_1language
  (JNIEnv *env, jclass clz, jlong parser, jlong lang_ptr){
    ts_parser_set_language((TSParser *) parser, (TSLanguage *) lang_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_version
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1version
  (JNIEnv *env, jclass clz, jlong lang_ptr){
    return ts_language_version((TSLanguage *) lang_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_parse_string
 * Signature: (JJLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1parse_1string
  (JNIEnv *env, jclass clz, jlong parser, jlong old_tree, jstring input){
    const char *str = (*env)->GetStringUTFChars(env, input, NULL);
    TSTree *tree = ts_parser_parse_string(
        (TSParser *)parser,
        (TSTree *) old_tree,
        str,
        (*env)->GetStringUTFLength(env, input)
    );
    (*env)->ReleaseStringUTFChars(env, input, str);
    return (jlong) tree;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_parse_string_encoding
 * Signature: (JJLjava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1parse_1string_1encoding
  (JNIEnv *env, jclass clz, jlong parser_ptr, jlong old_tree_ptr, jstring input, jint ts_encoding){
    const char *str = (*env)->GetStringUTFChars(env, input, NULL);
    TSTree *ts_tree = ts_parser_parse_string_encoding(
      (TSParser *) parser_ptr,
      (TSTree *)old_tree_ptr,
      str,
      (*env)->GetStringUTFLength(env, input),
      ts_encoding
    );
    (*env)->ReleaseStringUTFChars(env, input, str);
    return (jlong) ts_tree;
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_reset
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1reset
  (JNIEnv *env, jclass clz, jlong parser_ptr){
    ts_parser_reset((TSParser *) parser_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_set_timeout_micros
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1set_1timeout_1micros
  (JNIEnv *env, jclass clz, jlong parser_ptr, jlong timeout){
    ts_parser_set_timeout_micros((TSParser *) parser_ptr, timeout);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_timeout_micros
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1timeout_1micros
  (JNIEnv *env, jclass clz, jlong parser_ptr){
    return ts_parser_timeout_micros((TSParser *) parser_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_set_cancellation_flag
 * Signature: (JJ)J
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1set_1cancellation_1flag
  (JNIEnv *env, jclass clz, jlong parser_ptr, jlong flag_ptr){
    ts_parser_set_cancellation_flag((TSParser *) parser_ptr, (size_t *) flag_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_cancellation_flag
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1cancellation_1flag
  (JNIEnv *env, jclass clz, jlong parser_ptr){
    return (jlong) ts_parser_cancellation_flag((TSParser *) parser_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    alloc_cancellation_flag
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_alloc_1cancellation_1flag
  (JNIEnv *env, jclass clz){
    return (jlong) malloc(sizeof(size_t));
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    get_cancellation_flag_value
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_get_1cancellation_1flag_1value
  (JNIEnv *env, jclass clz, jlong flag_ptr){
  return (jlong) *((size_t *) flag_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    free_cancellation_flag
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_free_1cancellation_1flag
  (JNIEnv *env, jclass clz, jlong flag_ptr){
    free((size_t *) flag_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    write_cancellation_flag
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_write_1cancellation_1flag
  (JNIEnv *env, jclass clz, jlong flag_ptr, jlong value){
    *((size_t *) flag_ptr) = value;
}

void ts_log(void *payload, TSLogType logType, const char *str){
    JNIEnv *env;
    int ret = (*java_vm)->GetEnv(java_vm, (void **)&env, JNI_VERSION_10);
    if (ret == JNI_EDETACHED) {
        ret = (*java_vm)->AttachCurrentThread(java_vm, (void **) &env, NULL);
        if (ret != 0) {
            printf("getenv(): can't attach `logger` to current thread\n");
            return;
        }
    } else if (ret == JNI_EVERSION) {
        printf("getenv(): jni version error\n");
        return;
    }
    jobject ts_logger_object = (jobject) payload;
    jclass ts_logger_class = (*env)->GetObjectClass(env, ts_logger_object);
    jmethodID log_method = (*env)->GetMethodID(env, ts_logger_class, "log", TS_LOGGER_LOG_METHOD_SIGNATURE);
    jclass ts_log_type_class = (*env)->FindClass(env, TS_LOG_TYPE_CLASS_NAME);
    // TODO refactor enum to a function
    jfieldID enum_field;
    switch(logType){
        case TSLogTypeParse:
            enum_field = (*env)->GetStaticFieldID(env, ts_log_type_class , "TSLogTypeParse", TS_LOG_TYPE_SIGNATURE);
            break;
        case TSLogTypeLex:
            enum_field = (*env)->GetStaticFieldID(env, ts_log_type_class , "TSLogTypeLex", TS_LOG_TYPE_SIGNATURE);
            break;
        default:
            (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/RuntimeException"), "Can't handle TSLogType");
    }
    jobject enum_value = (* env)->GetStaticObjectField(env, ts_log_type_class, enum_field);
    jstring message = (*env)->NewStringUTF(env, str);
    (*env)->CallVoidMethod(env, ts_logger_object, log_method, enum_value, message);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_set_logger
 * Signature: (JLorg/treesitter/TSLogger;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1set_1logger
  (JNIEnv *env, jclass clz, jlong parser_ptr, jobject ts_logger_object){
    TSLogger logger = ts_parser_logger((TSParser *) parser_ptr);
    if(logger.payload != NULL){
        (*env)->DeleteGlobalRef(env, (jobject) logger.payload);
    }
    jobject ref = (*env)->NewGlobalRef(env, ts_logger_object);
    logger.payload = (void *) ref;
    logger.log = ts_log;
    ts_parser_set_logger((TSParser *) parser_ptr, logger);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    free_logger
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_free_1logger
  (JNIEnv *env, jclass clz, jlong parser_ptr){
  TSLogger logger = ts_parser_logger((TSParser *) parser_ptr);
  if(logger.payload != NULL){
      (*env)->DeleteGlobalRef(env, (jobject) logger.payload);
  }
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_print_dot_graphs
 * Signature: (JLjava/io/FileDescriptor;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1print_1dot_1graphs
  (JNIEnv *env, jclass clz, jlong parser_ptr, jobject fd_object){
   jclass fd_class = (*env)->GetObjectClass(env, fd_object);
   jlong fd = (*env)->GetIntField(env, fd_object, ts_jni_get_field_id(env, fd_class, "fd", "I"));
   if(fd == -1){
     fd = (*env)->GetLongField(env, fd_object, ts_jni_get_field_id(env, fd_class, "handle", "J"));
   }
   ts_parser_print_dot_graphs((TSParser *) parser_ptr, fd);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1tree_1copy
  (JNIEnv *env, jclass clz, jlong tree_ptr){
    return (jlong) ts_tree_copy((TSTree *) tree_ptr);
}

const char *TSQueryErrorNames[] = {
    "TSQueryErrorNone"
    "TSQueryErrorSyntax",
    "TSQueryErrorNodeType",
    "TSQueryErrorField",
    "TSQueryErrorCapture",
    "TSQueryErrorStructure",
    "TSQueryErrorLanguage",
};

char TSQueryErrorBuffer[64];

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_new
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1query_1new
  (JNIEnv *env, jclass clz, jlong ts_language_ptr, jstring string){
  const char* str = (*env)->GetStringUTFChars(env, string, NULL);
  uint32_t error_offset;
  TSQueryError error_type;
  TSQuery *query = ts_query_new(
    (const TSLanguage *) ts_language_ptr,
    str,
    (*env)->GetStringUTFLength(env, string),
    &error_offset,
    &error_type
  );
  (*env)->ReleaseStringUTFChars(env, string, str);
  if(query == NULL){
    sprintf(TSQueryErrorBuffer, "Invalid query: %s at offset %d", TSQueryErrorNames[error_type], error_offset);
    (*env)->ThrowNew(env, (*env)->FindClass(env, TS_QUERY_EXCEPTION_CLASS_NAME), TSQueryErrorBuffer);
  }
  return (jlong) query;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1delete
  (JNIEnv *env, jclass clz, jlong ts_query_ptr){
    ts_query_delete((TSQuery *) ts_query_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_pattern_count
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1pattern_1count
  (JNIEnv *env, jclass clz, jlong ts_query_ptr){
    return ts_query_pattern_count((TSQuery *) ts_query_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_capture_count
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1capture_1count
  (JNIEnv *env, jclass clz, jlong ts_query_ptr){
    return ts_query_capture_count((TSQuery *) ts_query_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_string_count
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1string_1count
  (JNIEnv *env, jclass clz, jlong ts_query_ptr){
    return ts_query_string_count((TSQuery *) ts_query_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_start_byte_for_pattern
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1start_1byte_1for_1pattern
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint patter_index){
    return ts_query_start_byte_for_pattern((TSQuery *) ts_query_ptr, patter_index);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_predicates_for_pattern
 * Signature: (JI)[Lorg/treesitter/TSQueryPredicateStep;
 */
JNIEXPORT jobjectArray JNICALL Java_org_treesitter_TSParser_ts_1query_1predicates_1for_1pattern
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint patter_index){
    uint32_t length;
    const TSQueryPredicateStep *step = ts_query_predicates_for_pattern(
      (const TSQuery *) ts_query_ptr,
      patter_index,
      &length
    );
    jclass step_class = (*env)->FindClass(env, TS_QUERY_PREDICATE_STEP_CLASS_NAME);
    jobjectArray step_objects = (*env)->NewObjectArray(env, length, step_class, NULL);
    for(int i = 0; i < length; i++){
        (*env)->SetObjectArrayElement(env, step_objects, i, ts_query_predicate_step_to_obj(env, step));
        step++;
    }
    return step_objects;
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_is_pattern_rooted
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1is_1pattern_1rooted
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint patter_index){
    return ts_query_is_pattern_rooted((TSQuery *) ts_query_ptr, patter_index);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_is_pattern_non_local
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1is_1pattern_1non_1local
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint patter_index){
    return ts_query_is_pattern_non_local((TSQuery *) ts_query_ptr, patter_index);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_is_pattern_guaranteed_at_step
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1is_1pattern_1guaranteed_1at_1step
 (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint byte_offset){
   return ts_query_is_pattern_guaranteed_at_step((TSQuery *) ts_query_ptr, byte_offset);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_capture_name_for_id
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1query_1capture_1name_1for_1id
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint index){
    uint32_t length;
    const char *str = ts_query_capture_name_for_id(
        (const TSQuery *) ts_query_ptr,
        index,
        &length
    );
    if(str == NULL){
        return NULL;
    }
    return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_capture_quantifier_for_id
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1capture_1quantifier_1for_1id
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint pattern_id, jint capture_id){
    return ts_query_capture_quantifier_for_id((const TSQuery *) ts_query_ptr, pattern_id, capture_id);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_string_value_for_id
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1query_1string_1value_1for_1id
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint index){
    uint32_t length;
    const char *str = ts_query_string_value_for_id(
      (const TSQuery *) ts_query_ptr,
      index,
      &length
    );
    return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_disable_capture
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1disable_1capture
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jstring name){
    const char* str = (*env)->GetStringUTFChars(env, name, NULL);
    ts_query_disable_capture((TSQuery *) ts_query_ptr, str, (*env)->GetStringUTFLength(env, name));
     (*env)->ReleaseStringUTFChars(env, name, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_disable_pattern
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1disable_1pattern
  (JNIEnv *env, jclass clz, jlong ts_query_ptr, jint pattern_index){
    ts_query_disable_pattern((TSQuery *) ts_query_ptr, pattern_index);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_new
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1new
  (JNIEnv *env, jclass clz){
    return (jlong) ts_query_cursor_new();
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1delete
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr){
    ts_query_cursor_delete((TSQueryCursor *) query_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_exec
 * Signature: (JJLorg/treesitter/TSNode;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1exec
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jlong query_ptr, jobject ts_node_object){
    ts_query_cursor_exec(
      (TSQueryCursor *) query_cursor_ptr,
      (TSQuery *) query_ptr,
      ts_node_from_obj(env, ts_node_object)
    );
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_did_exceed_match_limit
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1did_1exceed_1match_1limit
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr){
   return ts_query_cursor_did_exceed_match_limit((TSQueryCursor *) query_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_match_limit
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1match_1limit
 (JNIEnv *env, jclass clz, jlong query_cursor_ptr){
    return ts_query_cursor_match_limit((TSQueryCursor *) query_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_set_match_limit
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1set_1match_1limit
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jint limit){
    ts_query_cursor_set_match_limit((TSQueryCursor *) query_cursor_ptr, limit);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_set_byte_range
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1set_1byte_1range
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jint start_byte, jint end_byte){
    ts_query_cursor_set_byte_range((TSQueryCursor *) query_cursor_ptr, start_byte, end_byte);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_set_point_range
 * Signature: (JLorg/treesitter/TSPoint;Lorg/treesitter/TSPoint;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1set_1point_1range
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jobject start_point, jobject end_point){
      ts_query_cursor_set_point_range(
         (TSQueryCursor *) query_cursor_ptr,
         ts_point_from_obj(env, start_point),
         ts_point_from_obj(env, end_point)
      );
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_next_match
 * Signature: (JLorg/treesitter/TSQueryMatch;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1next_1match
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jobject ts_match_obj){
    TSQueryMatch match;
    bool ret = ts_query_cursor_next_match((TSQueryCursor *) query_cursor_ptr, &match);
    if(ret){
        copy_match(env, &match,  -1, ts_match_obj);
    }
    return ret;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_remove_match
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1remove_1match
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jint match_id){
    ts_query_cursor_remove_match((TSQueryCursor *) query_cursor_ptr, match_id);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_query_cursor_next_capture
 * Signature: (JLorg/treesitter/TSQueryMatch;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1query_1cursor_1next_1capture
  (JNIEnv *env, jclass clz, jlong query_cursor_ptr, jobject ts_match_obj){
   uint32_t capture_index;
   TSQueryMatch match;
   bool ret = ts_query_cursor_next_capture((TSQueryCursor *) query_cursor_ptr, &match, &capture_index);
   if(ret){
       copy_match(env, &match, capture_index, ts_match_obj);
   }
   return ret;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_print_dot_graph
 * Signature: (JLjava/io/FileDescriptor;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1tree_1print_1dot_1graph
  (JNIEnv *env, jclass clz, jlong tree_ptr, jobject fd_object){
    jclass fd_class = (*env)->GetObjectClass(env, fd_object);
    jlong fd = (*env)->GetIntField(env, fd_object, ts_jni_get_field_id(env, fd_class, "fd", "I"));
    if(fd == -1){
        fd = (*env)->GetLongField(env, fd_object, ts_jni_get_field_id(env, fd_class, "handle", "J"));
    }
    ts_tree_print_dot_graph((TSTree *) tree_ptr, fd);
}


struct read_payload{
    jobject ts_reader_object;
    JNIEnv *env;
    jbyteArray buffer;
    jbyte *str_buf;
};

const char *ts_read(void *payload, uint32_t offset, TSPoint position, uint32_t *bytes_read){
    struct read_payload *read_payload = (struct read_payload *) payload;
    JNIEnv *env = read_payload->env;
    jbyteArray buffer = read_payload->buffer;
    jobject ts_reader_object = read_payload->ts_reader_object;
    jclass ts_read_class = (*env)->GetObjectClass(env, ts_reader_object);
    jmethodID read_method = (*env)->GetMethodID(env, ts_read_class, "read", TS_READER_READ_METHOD_SIGNATURE);
    jobject ts_point_object = ts_point_to_obj(env, position);
    *bytes_read = (*env)->CallIntMethod(env, ts_reader_object, read_method, buffer, offset, ts_point_object);
    if(*bytes_read > 0){
        (*env)->GetByteArrayRegion(env, buffer, 0, *bytes_read, read_payload->str_buf);
    }
    return (const char *)read_payload->str_buf;
}

#define READER_BUF_SIZE 1024 * 4
/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_parse
 * Signature: (JJLjava/io/Reader;I)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1parse
  (JNIEnv *env, jclass clz, jlong parser_ptr, jbyteArray buf, jlong old_tree_ptr, jobject ts_reader_object, jint ts_encoding){
    struct read_payload payload;
    jint buf_len = (*env)->GetArrayLength(env, buf);
    payload.ts_reader_object = ts_reader_object;
    payload.env = env;
    payload.buffer = buf;
    payload.str_buf = (jbyte *) malloc(buf_len);
    TSInput ts_input;
    ts_input.payload = (void *) &payload;
    ts_input.encoding = ts_encoding;
    ts_input.read = ts_read;

    TSTree *ts_tree = ts_parser_parse(
      (TSParser *) parser_ptr,
      (TSTree *) old_tree_ptr,
      ts_input
    );
    free(payload.str_buf);
    return (jlong) ts_tree;
}



/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_root_node
 * Signature: (J)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1tree_1root_1node
  (JNIEnv *env, jclass clz, jlong tree_ptr){
    TSNode root_node = ts_tree_root_node((TSTree *) tree_ptr);
    return ts_node_to_obj(env, root_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_root_node_with_offset
 * Signature: (JILorg/treesitter/TSPoint;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1tree_1root_1node_1with_1offset
  (JNIEnv *env, jclass clz, jlong tree_ptr, jint offset_bytes, jobject offset_point_object){
  TSPoint ts_offset_point = ts_point_from_obj(env, offset_point_object);
  TSNode ts_node = ts_tree_root_node_with_offset((TSTree *) tree_ptr, offset_bytes, ts_offset_point);
  return ts_node_to_obj(env, ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_language
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1tree_1language
  (JNIEnv *env, jclass clz, jlong tree_ptr){
  return (jlong) ts_tree_language((TSTree *) tree_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_included_ranges
 * Signature: (J)[Lorg/treesitter/TSRange;
 */
JNIEXPORT jobjectArray JNICALL Java_org_treesitter_TSParser_ts_1tree_1included_1ranges
  (JNIEnv *env, jclass clz, jlong tree_ptr){
    uint32_t length;
    const TSRange *ts_range = ts_tree_included_ranges(
          (const TSTree *) tree_ptr,
          &length
        );
    const TSRange *range_start = ts_range;
    jclass ts_range_class = ts_jni_find_class(env, TS_RANGE_CLASS_NAME);
    jobjectArray range_objects = (*env)->NewObjectArray(env, length, ts_range_class, NULL);
    if(length > 0){
       for(int i = 0; i < length; i++){
          (*env)->SetObjectArrayElement(env, range_objects, i, ts_range_to_obj(env, ts_range));
          ts_range++;
      }
      free((void *) range_start);
    }

    return range_objects;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_edit
 * Signature: (JLorg/treesitter/TSInputEdit;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1tree_1edit
  (JNIEnv *env, jclass clz, jlong tree_ptr, jobject ts_input_edit_object){
   TSInputEdit edit;
   ts_input_edit_from_obj(env, ts_input_edit_object, &edit);
   ts_tree_edit((TSTree *) tree_ptr, &edit);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_get_changed_ranges
 * Signature: (JJ)[Lorg/treesitter/TSRange;
 */
JNIEXPORT jobjectArray JNICALL Java_org_treesitter_TSParser_ts_1tree_1get_1changed_1ranges
  (JNIEnv *env, jclass clz, jlong old_tree_ptr, jlong new_tree_ptr){
  uint32_t length;
  const TSRange *ts_range = ts_tree_get_changed_ranges(
        (TSTree *) old_tree_ptr,
        (TSTree *) new_tree_ptr,
        &length
  );
  const TSRange *range_start = ts_range;
  jclass ts_range_class = ts_jni_find_class(env, TS_RANGE_CLASS_NAME);
  jobjectArray range_objects = (*env)->NewObjectArray(env, length, ts_range_class, NULL);
  if(length > 0){
    for(int i = 0; i < length; i++){
        (*env)->SetObjectArrayElement(env, range_objects, i, ts_range_to_obj(env, ts_range));
        ts_range++;
    }
    free((void *)range_start);
  }
  return range_objects;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_named_child
 * Signature: (Lorg/treesitter/TSNode;I)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1named_1child
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint index){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child_ts_node = ts_node_named_child(ts_node, 0);
    return ts_node_to_obj(env, child_ts_node);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_type
 * Signature: (Lorg/treesitter/TSNode;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1node_1type
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (*env)->NewStringUTF(env, ts_node_type(ts_node));
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_symbol
 * Signature: (Lorg/treesitter/TSNode;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1node_1symbol
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return ts_node_symbol(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_child_count
 * Signature: (Lorg/treesitter/TSNode;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1node_1child_1count
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return ts_node_child_count(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_named_child_count
 * Signature: (Lorg/treesitter/TSNode;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1node_1named_1child_1count
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return ts_node_named_child_count(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1tree_1delete
  (JNIEnv *env, jclass clz, jlong tree_ptr){
    ts_tree_delete((TSTree *) tree_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1parser_1delete
  (JNIEnv *env, jclass clz, jlong parer_ptr){
    ts_parser_delete((TSParser *) parer_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_string
 * Signature: (Lorg/treesitter/TSNode;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1node_1string
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    char *string = ts_node_string(ts_node);
    jstring str = (*env)->NewStringUTF(env, string);
    free(string);
    return str;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_start_byte
 * Signature: (Lorg/treesitter/TSNode;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1node_1start_1byte
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return ts_node_start_byte(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_end_byte
 * Signature: (Lorg/treesitter/TSNode;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1node_1end_1byte
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return ts_node_end_byte(ts_node);
}
/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_start_point
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSPoint;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1start_1point
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSPoint ts_point = ts_node_start_point(ts_node);
    return ts_point_to_obj(env, ts_point);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_end_point
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSPoint;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1end_1point
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSPoint ts_point = ts_node_end_point(ts_node);
    return ts_point_to_obj(env, ts_point);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_parent
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1parent
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode parent_node = ts_node_parent(ts_node);
    return ts_node_to_obj(env, parent_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_child
 * Signature: (Lorg/treesitter/TSNode;I)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1child
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint index){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child_node = ts_node_child(ts_node, index);
    return ts_node_to_obj(env, child_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_field_name_for_child
 * Signature: (Lorg/treesitter/TSNode;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1node_1field_1name_1for_1child
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint index){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    const char *str = ts_node_field_name_for_child(ts_node, index);
    if(str == NULL){
        return NULL;
    }
    return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_is_null
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1is_1null
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_is_null(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_is_named
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1is_1named
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_is_named(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_is_missing
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1is_1missing
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_is_missing(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_is_extra
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1is_1extra
(JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_is_extra(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_has_changes
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1has_1changes
(JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_has_changes(ts_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_has_error
 * Signature: (Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1has_1error
(JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    return (jboolean) ts_node_has_error(ts_node);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_next_named_sibling
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1next_1named_1sibling
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode next_sibling = ts_node_next_named_sibling(ts_node);
    return ts_node_to_obj(env, next_sibling);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_prev_named_sibling
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1prev_1named_1sibling
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode prev_sibling = ts_node_prev_named_sibling(ts_node);
    return ts_node_to_obj(env, prev_sibling);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_first_child_for_byte
 * Signature: (Lorg/treesitter/TSNode;I)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1first_1child_1for_1byte
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint start_byte){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_first_child_for_byte(ts_node, start_byte);
    return ts_node_to_obj(env, child);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_first_named_child_for_byte
 * Signature: (Lorg/treesitter/TSNode;I)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1first_1named_1child_1for_1byte
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint start_byte){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_first_named_child_for_byte(ts_node, start_byte);
    return ts_node_to_obj(env, child);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_descendant_for_byte_range
 * Signature: (Lorg/treesitter/TSNode;II)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1descendant_1for_1byte_1range
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint start_byte, jint end_byte){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_descendant_for_byte_range(ts_node, start_byte, end_byte);
    return ts_node_to_obj(env, child);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_descendant_for_point_range
 * Signature: (Lorg/treesitter/TSNode;Lorg/treesitter/TSPoint;Lorg/treesitter/TSPoint;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1descendant_1for_1point_1range
  (JNIEnv *env, jclass clz, jobject ts_node_object, jobject start_ts_point, jobject end_ts_point){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_descendant_for_point_range(ts_node, ts_point_from_obj(env, start_ts_point), ts_point_from_obj(env, end_ts_point));
    return ts_node_to_obj(env, child);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_named_descendant_for_byte_range
 * Signature: (Lorg/treesitter/TSNode;II)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1named_1descendant_1for_1byte_1range
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint start_byte, jint end_byte){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_named_descendant_for_byte_range(ts_node, start_byte, end_byte);
    return ts_node_to_obj(env, child);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_named_descendant_for_point_range
 * Signature: (Lorg/treesitter/TSNode;Lorg/treesitter/TSPoint;Lorg/treesitter/TSPoint;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1named_1descendant_1for_1point_1range
  (JNIEnv *env, jclass clz, jobject ts_node_object, jobject start_ts_point, jobject end_ts_point){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child = ts_node_named_descendant_for_point_range(ts_node, ts_point_from_obj(env, start_ts_point), ts_point_from_obj(env, end_ts_point));
    return ts_node_to_obj(env, child);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_edit
 * Signature: (Lorg/treesitter/TSNode;Lorg/treesitter/TSInputEdit;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1edit
  (JNIEnv *env, jclass clz, jobject ts_node_object, jobject ts_input_edit_object){
   TSInputEdit edit;
   TSNode ts_node = ts_node_from_obj(env, ts_node_object);
   ts_input_edit_from_obj(env, ts_input_edit_object, &edit);
   ts_node_edit(&ts_node, &edit);
   return ts_node_to_obj(env, ts_node);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_eq
 * Signature: (Lorg/treesitter/TSNode;Lorg/treesitter/TSNode;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1node_1eq
  (JNIEnv *env, jclass clz, jobject ts_node_object_a, jobject ts_node_object_b){
    TSNode a = ts_node_from_obj(env, ts_node_object_a);
    TSNode b = ts_node_from_obj(env, ts_node_object_b);
    return ts_node_eq(a, b);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    free_cursor
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_free_1cursor
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    free((void *) ts_tree_cursor_ptr);
}

TSTreeCursor *alloc_tree_cursor(TSTreeCursor cursor){
    TSTreeCursor *cursor_ptr = (TSTreeCursor *) malloc(sizeof(TSTreeCursor));
    cursor_ptr->tree = cursor.tree;
    cursor_ptr->id = cursor.id;
    cursor_ptr->context[0] = cursor.context[0];
    cursor_ptr->context[1] = cursor.context[1];
    return cursor_ptr;
}
/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_new
 * Signature: (Lorg/treesitter/TSNode;)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1new
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode node = ts_node_from_obj(env, ts_node_object);
    TSTreeCursor cursor = ts_tree_cursor_new(node);
    return (jlong) alloc_tree_cursor(cursor);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1delete
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    ts_tree_cursor_delete((TSTreeCursor *) ts_tree_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_reset
 * Signature: (JLorg/treesitter/TSNode;)V
 */
JNIEXPORT void JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1reset
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr, jobject ts_node_object){
    ts_tree_cursor_reset((TSTreeCursor *) ts_tree_cursor_ptr, ts_node_from_obj(env, ts_node_object));
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_current_node
 * Signature: (J)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1current_1node
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    return ts_node_to_obj(env, ts_tree_cursor_current_node((TSTreeCursor *) ts_tree_cursor_ptr));
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_current_field_name
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1current_1field_1name
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    const char *str = ts_tree_cursor_current_field_name((TSTreeCursor *) ts_tree_cursor_ptr);
    return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_current_field_id
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1current_1field_1id
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    return ts_tree_cursor_current_field_id((TSTreeCursor *) ts_tree_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_goto_parent
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1goto_1parent
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    return ts_tree_cursor_goto_parent((TSTreeCursor *) ts_tree_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_goto_next_sibling
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1goto_1next_1sibling
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    return ts_tree_cursor_goto_next_sibling((TSTreeCursor *) ts_tree_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_goto_first_child
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1goto_1first_1child
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    return ts_tree_cursor_goto_first_child((TSTreeCursor *) ts_tree_cursor_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_goto_first_child_for_byte
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1goto_1first_1child_1for_1byte
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr, jint start_byte){
    return ts_tree_cursor_goto_first_child_for_byte((TSTreeCursor *) ts_tree_cursor_ptr, start_byte);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_goto_first_child_for_point
 * Signature: (JLorg/treesitter/TSPoint;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1goto_1first_1child_1for_1point
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr, jobject ts_point_object){
    TSPoint startPoint = ts_point_from_obj(env, ts_point_object);
    return ts_tree_cursor_goto_first_child_for_point((TSTreeCursor *) ts_tree_cursor_ptr, startPoint);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_tree_cursor_copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1tree_1cursor_1copy
  (JNIEnv *env, jclass clz, jlong ts_tree_cursor_ptr){
    TSTreeCursor cursor = ts_tree_cursor_copy((TSTreeCursor *) ts_tree_cursor_ptr);
    return (jlong) alloc_tree_cursor(cursor);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_next_sibling
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1next_1sibling
  (JNIEnv *env, jclass clz, jobject ts_node_object){
      TSNode ts_node = ts_node_from_obj(env, ts_node_object);
      TSNode prev_sibling = ts_node_next_sibling(ts_node);
      return ts_node_to_obj(env, prev_sibling);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_prev_sibling
 * Signature: (Lorg/treesitter/TSNode;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1prev_1sibling
  (JNIEnv *env, jclass clz, jobject ts_node_object){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode prev_sibling = ts_node_prev_sibling(ts_node);
    return ts_node_to_obj(env, prev_sibling);
}


/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_child_by_field_name
 * Signature: (Lorg/treesitter/TSNode;Ljava/lang/String;)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1child_1by_1field_1name
  (JNIEnv *env, jclass clz, jobject ts_node_object, jstring field_name){
    const char *str = (*env)->GetStringUTFChars(env, field_name, NULL);
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child_node = ts_node_child_by_field_name(
        ts_node,
        str,
        (*env)->GetStringUTFLength(env, field_name)
    );
    (*env)->ReleaseStringUTFChars(env, field_name, str);
    return ts_node_to_obj(env, child_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_field_count
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1field_1count
  (JNIEnv *env, jclass clz, jlong lang_ptr){
    return ts_language_field_count((const TSLanguage *) lang_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_field_name_for_id
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1language_1field_1name_1for_1id
  (JNIEnv *env, jclass clz, jlong lang_ptr, jint field_id){
    const char *str = ts_language_field_name_for_id((const TSLanguage *) lang_ptr, (TSFieldId) field_id);
    return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_field_id_for_name
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1field_1id_1for_1name
  (JNIEnv *env, jclass clz, jlong lang_ptr, jstring field_name){
    const char *str = (*env)->GetStringUTFChars(env, field_name, NULL);
    TSFieldId field_id = ts_language_field_id_for_name(
        (const TSLanguage *) lang_ptr,
        str,
        (*env)->GetStringUTFLength(env, field_name)
    );
    (*env)->ReleaseStringUTFChars(env, field_name, str);
    return (jint) field_id;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_node_child_by_field_id
 * Signature: (Lorg/treesitter/TSNode;I)Lorg/treesitter/TSNode;
 */
JNIEXPORT jobject JNICALL Java_org_treesitter_TSParser_ts_1node_1child_1by_1field_1id
  (JNIEnv *env, jclass clz, jobject ts_node_object, jint field_id){
    TSNode ts_node = ts_node_from_obj(env, ts_node_object);
    TSNode child_node = ts_node_child_by_field_id(ts_node, (TSFieldId) field_id);
    return ts_node_to_obj(env, child_node);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_language
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TSParser_ts_1parser_1language
  (JNIEnv *env, jclass clz, jlong parser_ptr){
   return (jlong) ts_parser_language((TSParser *) parser_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_set_included_ranges
 * Signature: (J[Lorg/treesitter/TSRange;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_treesitter_TSParser_ts_1parser_1set_1included_1ranges
  (JNIEnv *env, jclass clz, jlong parser_ptr, jobjectArray range_objects){
    jsize range_objects_size = (*env)->GetArrayLength(env, range_objects);
    TSRange *ts_range = (TSRange *) malloc(range_objects_size * sizeof(TSRange));
    TSRange *ts_range_start = ts_range;
    for(int i = 0; i < range_objects_size; i++){
        jobject range_object = (*env)->GetObjectArrayElement(env, range_objects, i);
        ts_range_from_obj(env, range_object, ts_range);
        ts_range++;
    }
    bool ret = ts_parser_set_included_ranges(
      (TSParser *) parser_ptr,
      ts_range_start,
      range_objects_size
    );
    free(ts_range_start);
    return (jboolean) ret;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_parser_included_ranges
 * Signature: (J)[Lorg/treesitter/TSRange;
 */
JNIEXPORT jobjectArray JNICALL Java_org_treesitter_TSParser_ts_1parser_1included_1ranges
  (JNIEnv *env, jclass clz, jlong parser_ptr){
    uint32_t length;
    const TSRange *ts_range = ts_parser_included_ranges(
          (const TSParser *) parser_ptr,
          &length
        );
    jclass ts_range_class = ts_jni_find_class(env, TS_RANGE_CLASS_NAME);
    jobjectArray range_objects = (*env)->NewObjectArray(env, length, ts_range_class, NULL);
    for(int i = 0; i < length; i++){
        (*env)->SetObjectArrayElement(env, range_objects, i, ts_range_to_obj(env, ts_range));
        ts_range++;
    }
    return range_objects;
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_symbol_type
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1symbol_1type
  (JNIEnv *env, jclass clz, jlong lang_ptr, jint ts_symbol){
   return ts_language_symbol_type((const TSLanguage *) lang_ptr, (TSSymbol) ts_symbol);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_symbol_count
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1symbol_1count
  (JNIEnv *env, jclass clz, jlong lang_ptr){
   return ts_language_symbol_count((const TSLanguage *) lang_ptr);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_symbol_name
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_treesitter_TSParser_ts_1language_1symbol_1name
  (JNIEnv *env, jclass clz, jlong lang_ptr, jint ts_symbol){
   const char *str = ts_language_symbol_name((const TSLanguage *) lang_ptr, (TSSymbol) ts_symbol);
   return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     org_treesitter_TSParser
 * Method:    ts_language_symbol_for_name
 * Signature: (JLjava/lang/String;Z)I
 */
JNIEXPORT jint JNICALL Java_org_treesitter_TSParser_ts_1language_1symbol_1for_1name
  (JNIEnv *env, jclass clz, jlong lang_ptr, jstring name, jboolean is_named){
   const char *str = (*env)->GetStringUTFChars(env, name, NULL);
   TSSymbol ts_symbol = ts_language_symbol_for_name(
      (const TSLanguage *) lang_ptr,
      str,
      (*env)->GetStringUTFLength(env, name),
      is_named
   );
   (*env)->ReleaseStringUTFChars(env, name, str);
   return (jint) ts_symbol;
}