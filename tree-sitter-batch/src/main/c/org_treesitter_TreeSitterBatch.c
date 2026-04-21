
#include <jni.h>
void *tree_sitter_batch();
/*
 * Class:     org_treesitter_TreeSitterBatch
 * Method:    tree_sitter_batch
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterBatch_tree_1sitter_1batch
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_batch();
}
