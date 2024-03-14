
#include <jni.h>
void *tree_sitter_p4();
/*
 * Class:     org_treesitter_TreeSitterP4
 * Method:    tree_sitter_p4
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterP4_tree_1sitter_1p4
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_p4();
}
