
#include <jni.h>
void *tree_sitter_go();
/*
 * Class:     org_treesitter_TreeSitterGo
 * Method:    tree_sitter_go
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGo_tree_1sitter_1go
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_go();
}
