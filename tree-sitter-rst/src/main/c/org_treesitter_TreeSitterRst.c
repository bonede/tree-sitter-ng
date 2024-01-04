
#include <jni.h>
void *tree_sitter_rst();
/*
 * Class:     org_treesitter_TreeSitterRst
 * Method:    tree_sitter_rst
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRst_tree_1sitter_1rst
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_rst();
}
