
#include <jni.h>
void *tree_sitter_lean();
/*
 * Class:     org_treesitter_TreeSitterLean
 * Method:    tree_sitter_lean
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLean_tree_1sitter_1lean
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_lean();
}
