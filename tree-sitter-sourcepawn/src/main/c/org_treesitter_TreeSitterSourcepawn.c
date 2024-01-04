
#include <jni.h>
void *tree_sitter_sourcepawn();
/*
 * Class:     org_treesitter_TreeSitterSourcepawn
 * Method:    tree_sitter_sourcepawn
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSourcepawn_tree_1sitter_1sourcepawn
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_sourcepawn();
}
