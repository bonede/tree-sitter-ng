
#include <jni.h>
void *tree_sitter_rego();
/*
 * Class:     org_treesitter_TreeSitterRego
 * Method:    tree_sitter_rego
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRego_tree_1sitter_1rego
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_rego();
}
