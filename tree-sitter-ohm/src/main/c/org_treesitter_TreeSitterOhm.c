
#include <jni.h>
void *tree_sitter_ohm();
/*
 * Class:     org_treesitter_TreeSitterOhm
 * Method:    tree_sitter_ohm
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterOhm_tree_1sitter_1ohm
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_ohm();
}
