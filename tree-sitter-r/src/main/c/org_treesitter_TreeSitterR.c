
#include <jni.h>
void *tree_sitter_r();
/*
 * Class:     org_treesitter_TreeSitterR
 * Method:    tree_sitter_r
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterR_tree_1sitter_1r
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_r();
}
