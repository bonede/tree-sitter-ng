
#include <jni.h>
void *tree_sitter_meson();
/*
 * Class:     org_treesitter_TreeSitterMeson
 * Method:    tree_sitter_meson
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterMeson_tree_1sitter_1meson
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_meson();
}
