
#include <jni.h>
void *tree_sitter_make();
/*
 * Class:     org_treesitter_TreeSitterMake
 * Method:    tree_sitter_make
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterMake_tree_1sitter_1make
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_make();
}
