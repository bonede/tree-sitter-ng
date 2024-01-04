
#include <jni.h>
void *tree_sitter_fennel();
/*
 * Class:     org_treesitter_TreeSitterFennel
 * Method:    tree_sitter_fennel
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterFennel_tree_1sitter_1fennel
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_fennel();
}
