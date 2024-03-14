
#include <jni.h>
void *tree_sitter_tact();
/*
 * Class:     org_treesitter_TreeSitterTact
 * Method:    tree_sitter_tact
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterTact_tree_1sitter_1tact
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_tact();
}
