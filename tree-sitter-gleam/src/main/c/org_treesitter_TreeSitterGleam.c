
#include <jni.h>
void *tree_sitter_gleam();
/*
 * Class:     org_treesitter_TreeSitterGleam
 * Method:    tree_sitter_gleam
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGleam_tree_1sitter_1gleam
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_gleam();
}
