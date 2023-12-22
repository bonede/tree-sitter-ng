
#include <jni.h>
void *tree_sitter_bash();
/*
 * Class:     org_treesitter_TreeSitterBash
 * Method:    tree_sitter_bash
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterBash_tree_1sitter_1bash
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_bash();
}
