
#include <jni.h>
void *tree_sitter_hack();
/*
 * Class:     org_treesitter_TreeSitterHack
 * Method:    tree_sitter_hack
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterHack_tree_1sitter_1hack
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_hack();
}
