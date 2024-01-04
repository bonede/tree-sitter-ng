
#include <jni.h>
void *tree_sitter_query();
/*
 * Class:     org_treesitter_TreeSitterQuery
 * Method:    tree_sitter_query
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterQuery_tree_1sitter_1query
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_query();
}
