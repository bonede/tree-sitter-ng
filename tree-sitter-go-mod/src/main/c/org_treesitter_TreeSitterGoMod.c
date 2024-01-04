
#include <jni.h>
void *tree_sitter_gomod();
/*
 * Class:     org_treesitter_TreeSitterGoMod
 * Method:    tree_sitter_go_mod
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterGoMod_tree_1sitter_1go_1mod
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_gomod();
}
