
#include <jni.h>
void *tree_sitter_jq();
/*
 * Class:     org_treesitter_TreeSitterJq
 * Method:    tree_sitter_jq
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterJq_tree_1sitter_1jq
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_jq();
}
