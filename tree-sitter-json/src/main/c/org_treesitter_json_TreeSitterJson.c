
#include <jni.h>
void *tree_sitter_json();
/*
 * Class:     org_treesitter_json_TreeSitterJson
 * Method:    tree_sitter_json
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterJson_tree_1sitter_1json
  (JNIEnv *env, jclass clz){
    return (jlong) tree_sitter_json();
}