
#include <jni.h>
void *tree_sitter_elm();
/*
 * Class:     org_treesitter_TreeSitterElm
 * Method:    tree_sitter_elm
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterElm_tree_1sitter_1elm
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_elm();
}
