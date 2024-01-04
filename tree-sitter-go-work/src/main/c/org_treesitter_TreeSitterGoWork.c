
#include <jni.h>
void *tree_sitter_gowork();
/*
 * Class:     org_treesitter_TreeSitterGoWork
 * Method:    tree_sitter_go_work
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGoWork_tree_1sitter_1go_1work
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_gowork();
}
