
#include <jni.h>
void *tree_sitter_d();
/*
 * Class:     org_treesitter_TreeSitterD
 * Method:    tree_sitter_d
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterD_tree_1sitter_1d
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_d();
}
