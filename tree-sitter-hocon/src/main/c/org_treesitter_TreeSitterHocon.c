#include <jni.h>
void *tree_sitter_hocon();
/*
 * Class:     org_treesitter_TreeSitterHocon
 * Method:    tree_sitter_hocon
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterHocon_tree_1sitter_1hocon
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_hocon();
}
