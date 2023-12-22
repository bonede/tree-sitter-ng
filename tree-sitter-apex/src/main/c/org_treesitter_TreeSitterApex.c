#include <jni.h>
void *tree_sitter_apex();
/*
 * Class:     org_treesitter_TreeSitterApex
 * Method:    tree_sitter_apex
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterApex_tree_1sitter_1apex
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_apex();
}
