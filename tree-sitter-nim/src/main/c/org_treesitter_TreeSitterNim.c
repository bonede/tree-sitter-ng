
#include <jni.h>
void *tree_sitter_nim();
/*
 * Class:     org_treesitter_TreeSitterNim
 * Method:    tree_sitter_nim
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterNim_tree_1sitter_1nim
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_nim();
}
